/*******************************************************************************
 * Copyright (c) 2008, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.example.e4.rcp.todo.renderer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.renderers.swt.StackRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

@SuppressWarnings("restriction")
public class MyStackRenderer extends StackRenderer {

	private static final String STACK_BOTTOM = "StackBottom";

	@Override
	public Object createWidget(MUIElement element, Object parent) {
		try {
			if (!(element instanceof MPartStack) || !(parent instanceof Composite))
				return null;

			Composite parentComposite = (Composite) parent;

			// Ensure that all rendered PartStacks have an Id
			if (element.getElementId() == null
					|| element.getElementId().length() == 0) {
				String generatedId = "PartStack@" + Integer.toHexString(element.hashCode()); //$NON-NLS-1$
				element.setElementId(generatedId);
			}

			// TBD: need to define attributes to handle this
			final CTabFolder ctf = new CTabFolder(parentComposite, SWT.BORDER
					| (element.getTags().contains(STACK_BOTTOM) ? SWT.BOTTOM
							: SWT.NONE));
			Method getInitialMRUValue = StackRenderer.class.getDeclaredMethod(
					"getInitialMRUValue", Control.class);
			getInitialMRUValue.setAccessible(true);
			ctf.setMRUVisible((Boolean) getInitialMRUValue.invoke(this, ctf));

			// Adjust the minimum chars based on the location
			int location = modelService.getElementLocation(element);
			if ((location & EModelService.IN_SHARED_AREA) != 0) {
				Field minEditorChars = StackRenderer.class.getDeclaredField("MIN_EDITOR_CHARS");
				minEditorChars.setAccessible(true);
				ctf.setMinimumCharacters(minEditorChars.getInt(this));
				ctf.setUnselectedCloseVisible(true);
			} else {
				Field minViewChars = StackRenderer.class.getDeclaredField("MIN_VIEW_CHARS");
				minViewChars.setAccessible(true);
				ctf.setMinimumCharacters(minViewChars.getInt(this));
				ctf.setUnselectedCloseVisible(false);
			}

			bindWidget(element, ctf); // ?? Do we need this ?

			// Add a composite to manage the view's TB and Menu
			Method addTopRight = StackRenderer.class.getDeclaredMethod(
					"addTopRight", CTabFolder.class);
			addTopRight.setAccessible(true);
			addTopRight.invoke(this, ctf);

			return ctf;
		} catch (SecurityException e) {
			e.printStackTrace();
			return super.createWidget(element, parent);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return super.createWidget(element, parent);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return super.createWidget(element, parent);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return super.createWidget(element, parent);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return super.createWidget(element, parent);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return super.createWidget(element, parent);
		}
	}

	protected void updateTab(CTabItem cti, MPart part, String attName,
			Object newValue) {
		if (UIEvents.Dirtyable.DIRTY.equals(attName)) {
			Boolean dirtyState = (Boolean) newValue;
			String text = cti.getText();
			boolean hasAsterisk = text.length() > 0 && text.charAt(0) == '!';
			if (dirtyState.booleanValue()) {
				if (!hasAsterisk) {
					cti.setText('!' + text);
				}
			} else if (hasAsterisk) {
				cti.setText(text.substring(1));
			}
		} else {
			super.updateTab(cti, part, attName, newValue);
		}
	}
}
