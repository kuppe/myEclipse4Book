package com.example.e4.rcp.todo.lifecycle;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.dialog.PasswordDialog;

@SuppressWarnings("restriction")
public class LifeCycleManager {

	@PostContextCreate
	public void raisePasswordDialog(IEclipseContext context) {
		context.set(Shell.class, new Shell());
		PasswordDialog d = ContextInjectionFactory.make(PasswordDialog.class, context);
		if (Dialog.OK != d.open()) {
			// Wrong password
			System.exit(-1);
		}
	}
}
