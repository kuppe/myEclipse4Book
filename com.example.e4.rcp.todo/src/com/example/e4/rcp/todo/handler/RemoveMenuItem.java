package com.example.e4.rcp.todo.handler;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class RemoveMenuItem {
	private static final String MENU_ITEM_ID = "com.example.e4.rcp.todo.directmenuitem.2";
	
	public void execute(EModelService ems, MApplication searchRoot) {
		// EModelService does not find MMenu or Toolbar elements
		// see https://bugs.eclipse.org/383403
		@SuppressWarnings("unused")
		MUIElement find = ems.find(MENU_ITEM_ID,
				searchRoot);

		final List<MMenu> findElements = ems.findElements(searchRoot, null,
				MMenu.class, null);
		for (MMenu mMenu : findElements) {
			mMenu.setVisible(false);
		}
	}

	@Execute
	public void executeWithWorkaround(MTrimmedWindow trimmedWindow) {
		MMenuElement findMenu = findMenu(trimmedWindow, MENU_ITEM_ID);
		if (findMenu instanceof MMenu) {
			MMenu menu = (MMenu) findMenu;
			menu.setVisible(!menu.isVisible());
		} else if (findMenu instanceof MMenuItem) {
			MMenuItem item = (MMenuItem) findMenu;
			item.setVisible(!findMenu.isVisible());
		}
	}
	
	// Use a specific visitor to iterate the EMF model. This works independently
	// of e4's EModelService and simply uses the underlying EMF metamodel.
	// see https://bugs.eclipse.org/383403#c35
	private MMenuElement findMenu(final MUIElement uiElement, final String id) {
		if (uiElement instanceof MMenuElement
				&& uiElement.getElementId() != null && uiElement.getElementId().equals(id)) {
			return (MMenuElement) uiElement;
		}

		if (uiElement instanceof MTrimmedWindow) {
			MMenuElement findMenu = findMenu(
					((MTrimmedWindow) uiElement).getMainMenu(), id);
			if (findMenu != null) {
				return findMenu;
			}

		} else if (uiElement instanceof MPart) {
			List<MMenu> menus = ((MPart) uiElement).getMenus();
			for (MMenu mm : menus) {
				MMenuElement findMenu = findMenu(mm, id);
				if (findMenu != null) {
					return findMenu;
				}
			}
		} else if (uiElement instanceof MMenu) {
			List<MMenuElement> children = ((MMenu) uiElement).getChildren();
			for (MMenuElement me : children) {
				MMenuElement findMenu = findMenu(me, id);
				if (findMenu != null) {
					return findMenu;
				}
			}
		}
		return null;
	}
}