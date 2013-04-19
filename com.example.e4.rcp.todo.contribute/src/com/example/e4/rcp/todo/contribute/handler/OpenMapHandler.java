package com.example.e4.rcp.todo.contribute.handler;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class OpenMapHandler {
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell parent) {
		MessageDialog.openInformation(parent, "A Map", "Here should be a map");
	}
}