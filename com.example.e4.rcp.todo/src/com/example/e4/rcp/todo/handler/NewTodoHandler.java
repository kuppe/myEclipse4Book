package com.example.e4.rcp.todo.handler;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;
import com.example.e4.rcp.todo.model.UniqueTodo;
import com.example.e4.rcp.todo.wizard.TodoWizard;

public class NewTodoHandler {
	@Execute
	public void execute(Shell shell, final ITodoService model,
			MApplication application, @UniqueTodo final Todo todo) {

		IEclipseContext context = application.getContext();
		IEclipseContext childContext = context.createChild();
		childContext.set(Todo.class, todo);

		Wizard wizard = ContextInjectionFactory.make(TodoWizard.class,
				childContext);

		WizardDialog dialog = new WizardDialog(shell, wizard);
		if (dialog.open() == WizardDialog.OK) {
			Job j = new Job("Saving Todo") {
				@Override
				protected IStatus run(IProgressMonitor arg0) {
					model.saveTodo(todo);
					return Status.OK_STATUS;
				}
			};
			j.schedule();
		}
	}
}