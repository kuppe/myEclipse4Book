package com.example.e4.rcp.todo.contribute;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import com.example.e4.rcp.todo.contribute.handler.ExitHandlerWithoutCheck;

public class ModelProcessor {
	private static final String BUNDLE_NAME = "com.example.e4.rcp.todo.contribute";

	// Has to match the id in plugin.xml as well as the main application model
	@Inject
	@Named("org.eclipse.ui.file.menu")
	private MMenu menu;

	@Execute
	public void processModel(EModelService modelService,
			MApplication application) {
		// Remove the old exit menu entry
		if (menu != null && menu.getChildren() != null) {
			List<MMenuElement> list = new ArrayList<MMenuElement>();
			for (MMenuElement element : menu.getChildren()) {
				System.out.println(element);
				// Separaters have no label hence we
				// need to check for null
				if (element.getLabel() != null) {
					if (element.getLabel().contains("Exit")) {
						list.add(element);
					}
				}
			}
			menu.getChildren().removeAll(list);
		}

		// Now add a new menu entry
		MDirectMenuItem menuItem = MMenuFactory.INSTANCE.createDirectMenuItem();
		menuItem.setLabel("Exit");
		menuItem.setContributionURI("bundleclass://" + BUNDLE_NAME + "/"
				+ ExitHandlerWithoutCheck.class.getName());
		menu.getChildren().add(menuItem);
	}
}
