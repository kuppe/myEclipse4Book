package com.example.e4.rcp.todo.handler;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class RemoveTodoHandler {
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) Todo todo,
			ITodoService model) {
		model.deleteTodo(todo.getId());
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