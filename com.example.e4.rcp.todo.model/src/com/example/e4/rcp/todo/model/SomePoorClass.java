package com.example.e4.rcp.todo.model;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

@Creatable
@Singleton
public class SomePoorClass {
	private String name;

	@Inject
	public SomePoorClass(Dependency d) {
		this("Hi, I'm the real slim shady!", d);
	}

	public SomePoorClass(String name, Dependency d) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SomePoorClass [name=" + name + "]";
	}
}
