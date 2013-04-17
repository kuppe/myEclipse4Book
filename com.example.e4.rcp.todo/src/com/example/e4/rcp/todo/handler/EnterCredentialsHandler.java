package com.example.e4.rcp.todo.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.dialog.PasswordDialog;

public class EnterCredentialsHandler {
	@Execute
	public void execute(Shell shell) {
		PasswordDialog d = new PasswordDialog(shell);
		int open = d.open();
		if (open == IDialogConstants.OK_ID) {
			System.out.println(d.getUsername());
			System.out.println(d.getPassword());
		}
	}
}