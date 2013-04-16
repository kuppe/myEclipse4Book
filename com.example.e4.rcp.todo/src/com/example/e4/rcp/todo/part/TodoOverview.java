package com.example.e4.rcp.todo.part;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.example.e4.rcp.todo.model.ITodoService;

public class TodoOverview {

	@Inject
	public TodoOverview(Composite parent, ITodoService model) {
		int size = model.getTodos().size();
		Label label = new Label(parent, SWT.NONE);
		label.setText("Found todos #" + size);
	}
}