package com.example.e4.rcp.todo.addon;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;

import com.example.e4.rcp.todo.model.Dependency;
import com.example.e4.rcp.todo.model.SomePoorClass;

public class DependencyAddon {

	@PostConstruct
	void hookListeners(IEclipseContext ctx) {
		ctx.modify(SomePoorClass.class, new SomePoorClass("Some Other String",
				new Dependency()));
	}
}