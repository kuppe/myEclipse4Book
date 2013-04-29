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

import org.eclipse.e4.ui.internal.workbench.swt.CSSRenderingUtils;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.renderers.swt.StackRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class MyStackRenderer extends StackRenderer {

	private static final String STACK_BOTTOM = "StackBottom";

	@Override
	public Object createWidget(MUIElement element, Object parent) {
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
		ctf.setMRUVisible(getInitialMRUValue(ctf));

		// Adjust the minimum chars based on the location
		int location = modelService.getElementLocation(element);
		if ((location & EModelService.IN_SHARED_AREA) != 0) {
			ctf.setMinimumCharacters(MIN_EDITOR_CHARS);
			ctf.setUnselectedCloseVisible(true);
		} else {
			ctf.setMinimumCharacters(MIN_VIEW_CHARS);
			ctf.setUnselectedCloseVisible(false);
		}

		bindWidget(element, ctf); // ?? Do we need this ?

		// Add a composite to manage the view's TB and Menu
		addTopRight(ctf);

		return ctf;
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

	/*
	 * Ignore below this line (has simply been copied from the original class)
	 */

	// Minimum characters in for stacks outside the shared area
	private static int MIN_VIEW_CHARS = 1;

	// Minimum characters in for stacks inside the shared area
	private static int MIN_EDITOR_CHARS = 15;

	// View Menu / TB data constants
	private static final String TOP_RIGHT = "topRight"; //$NON-NLS-1$

	private boolean getInitialMRUValue(Control control) {
		boolean result = false;
		CSSRenderingUtils util = context.get(CSSRenderingUtils.class);
		if (util == null)
			return result;

		CSSValue value = util.getCSSValue(control,
				"MPartStack", "swt-mru-visible"); //$NON-NLS-1$ //$NON-NLS-2$

		if (value == null) {
			value = util.getCSSValue(control, "MPartStack", "mru-visible"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (value == null)
			return result;

		return Boolean.parseBoolean(value.getCssText());
	}

	/**
	 * @param ctf
	 */
	private void addTopRight(CTabFolder ctf) {
		Composite trComp = new Composite(ctf, SWT.NONE);
		trComp.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_DARK_CYAN));
		RowLayout rl = new RowLayout();
		trComp.setLayout(rl);
		rl.marginBottom = rl.marginTop = rl.marginRight = rl.marginLeft = 0;
		ctf.setData(TOP_RIGHT, trComp);
		ctf.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				Composite c = (Composite) e.widget.getData(TOP_RIGHT);
				if (c != null && !c.isDisposed())
					c.dispose();
			}
		});
	}
}
