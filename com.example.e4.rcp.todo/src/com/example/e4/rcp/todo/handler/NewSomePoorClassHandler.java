package com.example.e4.rcp.todo.handler;

import org.eclipse.e4.core.di.annotations.Execute;

import com.example.e4.rcp.todo.model.SomePoorClass;

public class NewSomePoorClassHandler {
	@Execute
	public void execute(SomePoorClass spc) {
		System.out.println(spc);
		System.out.println(spc.hashCode());
	}
}