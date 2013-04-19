package com.example.e4.rcp.todo.lifecycle;

import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.dialog.PasswordDialog;

@SuppressWarnings("restriction")
public class LifeCycleManager {

	@PostContextCreate
	public void raisePasswordDialog() {
		Shell shell = new Shell();
		PasswordDialog pwDialog = new PasswordDialog(shell);
		if (Dialog.OK != pwDialog.open()) {
			// Wrong password
			System.exit(-1);
		}
	}
}
