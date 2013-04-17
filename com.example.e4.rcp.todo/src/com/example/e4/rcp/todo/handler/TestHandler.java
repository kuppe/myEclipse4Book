package com.example.e4.rcp.todo.handler;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;

public class TestHandler {
	// The Id is set in the application model for the corresponding command
	private static final String COMMAND_PARAMETER_ID = "com.example.e4.rcp.todo.commandparameter.test";

	@Execute
	public void execute(@Optional @Named(COMMAND_PARAMETER_ID) String str) {
		System.out.println(str);
	}
}