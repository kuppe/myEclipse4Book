package com.example.e4.rcp.todo.tester;

import org.eclipse.core.expressions.PropertyTester;

import com.example.e4.rcp.todo.model.Todo;

public class TodoPropertyTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		if (receiver instanceof Todo) {
			Todo todo = (Todo) receiver;
			if ("isDone".equals(property)) {
				boolean state = ((Boolean) expectedValue);
				if (todo.isDone() == state) {
					return true;
				}
			}
		}
		return false;
	}
}
