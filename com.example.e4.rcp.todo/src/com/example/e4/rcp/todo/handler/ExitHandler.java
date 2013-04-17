package com.example.e4.rcp.todo.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class ExitHandler {
	@Execute
	public void execute(IWorkbench workbench, Shell shell) {
		boolean dialog = MessageDialog.openConfirm(shell, "Close Application",
				"Click ok to close the application");
		if (dialog) {
			workbench.close();
		}
	}
}