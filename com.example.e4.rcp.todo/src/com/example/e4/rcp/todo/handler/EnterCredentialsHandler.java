package com.example.e4.rcp.todo.handler;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;

import com.example.e4.rcp.todo.dialog.PasswordDialog;

public class EnterCredentialsHandler {

	@Execute
	public void execute(IEclipseContext context) {
		PasswordDialog d = ContextInjectionFactory.make(PasswordDialog.class, context);
		d.open();
	}
}