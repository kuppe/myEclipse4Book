package com.example.e4.rcp.todo.contribute.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;

public class ExitHandlerWithoutCheck {

	@Execute
	public void execute(IWorkbench workbench) {
		workbench.close();
	}
}
