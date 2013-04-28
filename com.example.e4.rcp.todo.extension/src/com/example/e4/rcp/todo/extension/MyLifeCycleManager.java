package com.example.e4.rcp.todo.extension;

import com.example.e4.rcp.todo.lifecycle.ILifecycleManager;

public class MyLifeCycleManager implements ILifecycleManager {

	@Override
	public void doSomething() {
		throw new RuntimeException("I'm evil hrhrhr");
	}
}
