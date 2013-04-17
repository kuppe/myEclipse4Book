package com.example.e4.rcp.todo.handler;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.example.e4.rcp.todo.model.Todo;

public class RemoveTodoHandler {
	@Execute
	public void execute() {
		System.out.println("Soon we will really delete todos");
	}

	@CanExecute
	public boolean canExecute(
			@Named(IServiceConstants.ACTIVE_SELECTION) IStructuredSelection selection) {
		Object element = selection.getFirstElement();
		if (element instanceof Todo) {
			Todo todo = (Todo) element;
			if (todo.isDone()) {
				return true;
			}
		}
		return false;
	}
}