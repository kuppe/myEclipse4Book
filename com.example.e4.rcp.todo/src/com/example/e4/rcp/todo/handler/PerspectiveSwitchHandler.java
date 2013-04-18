package com.example.e4.rcp.todo.handler;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class PerspectiveSwitchHandler {
	@Execute
	public void execute(MApplication app, EPartService partService,
			EModelService modelService, MPerspective activePerspective) {
		List<MPerspective> elements = modelService.findElements(app, null,
				MPerspective.class, null);
		for (MPerspective mPerspective : elements) {
			if (!mPerspective.equals(activePerspective)) {
				partService.switchPerspective(mPerspective);
			}
		}
	}
}