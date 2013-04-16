package com.example.e4.rcp.todo.part;

import javax.inject.Inject;

import org.eclipse.swt.widgets.Composite;

public class TodoOverview {

	@Inject
	public TodoOverview(Composite parent) {
		System.out.println("Wohoo, got composite via DI");
	}
}