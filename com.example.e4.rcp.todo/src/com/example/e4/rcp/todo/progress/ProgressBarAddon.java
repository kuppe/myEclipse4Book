package com.example.e4.rcp.todo.progress;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.ProgressProvider;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class ProgressBarAddon {
	// Id of a ToolControl that links to class ProgressBarToolItem
	private static final String PROGRESS_BAR = "com.example.e4.rcp.todo.toolcontrol.progressBar";

	@PostConstruct
	public void postConstruct(MApplication application,
			EModelService modelService, IJobManager jobManager) {

		// Find ProgressBarToolItem via EModelService
		MUIElement element = modelService.find(PROGRESS_BAR, application);
		final MToolControl control = (MToolControl) element;

		// Link progress to ProgressBarToolItem
		jobManager.setProgressProvider(new ProgressProvider() {
			@Override
			public IProgressMonitor createMonitor(Job job) {
				return (IProgressMonitor) control.getObject();
			}
		});
	}
}
