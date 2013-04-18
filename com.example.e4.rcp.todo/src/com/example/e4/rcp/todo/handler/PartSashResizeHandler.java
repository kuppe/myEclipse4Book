package com.example.e4.rcp.todo.handler;

import java.util.List;
import java.util.Random;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class PartSashResizeHandler {

	private final Random rnd = new Random();

	@Execute
	public void execute(EModelService modelService, MApplication application) {
		MPartSashContainer find = (MPartSashContainer) modelService.find(
				"com.example.e4.rcp.todo.partsashcontainer.0", application);
		List<MPartSashContainerElement> children = find.getChildren();
		for (MPartSashContainerElement mPartSashContainerElement : children) {
			mPartSashContainerElement.setContainerData(rnd.nextInt(100) + "");
			return;
		}
	}
}