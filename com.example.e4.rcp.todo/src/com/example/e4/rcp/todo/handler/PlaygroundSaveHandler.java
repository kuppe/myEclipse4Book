package com.example.e4.rcp.todo.handler;

import org.eclipse.e4.core.di.annotations.Execute;

public class PlaygroundSaveHandler {
	@Execute
	public void execute() {
		System.out.println("This is the playground save handler");
	}
}