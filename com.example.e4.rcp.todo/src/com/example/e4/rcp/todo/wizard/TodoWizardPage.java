package com.example.e4.rcp.todo.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.example.e4.rcp.todo.model.Todo;
import com.example.e4.rcp.todo.part.TodoDetailsPart;

public class TodoWizardPage extends WizardPage {

	private Todo todo;

	public TodoWizardPage(String pageName, Todo todo) {
		super(pageName);
		this.todo = todo;
		setTitle("New Todo");
		setDescription("Creates a new Todo");
	}

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);

		TodoDetailsPart tdp = new TodoDetailsPart();
		tdp.createPartControl(comp);
		tdp.setTodo(this.todo);

		// Mandatory call
		setControl(comp);
	}
}
