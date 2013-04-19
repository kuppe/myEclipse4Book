package com.example.e4.rcp.todo.service.internal;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.EventHandler;

public class DummyEventBroker implements IEventBroker {

	@Override
	public boolean send(String topic, Object data) {
		System.err.println("WARNING: Dummy EventBroker instance used");
		return true;
	}

	@Override
	public boolean post(String topic, Object data) {
		System.err.println("WARNING: Dummy EventBroker instance used");
		return true;
	}

	@Override
	public boolean subscribe(String topic, EventHandler eventHandler) {
		System.err.println("WARNING: Dummy EventBroker instance used");
		return true;
	}

	@Override
	public boolean subscribe(String topic, String filter,
			EventHandler eventHandler, boolean headless) {
		System.err.println("WARNING: Dummy EventBroker instance used");
		return true;
	}

	@Override
	public boolean unsubscribe(EventHandler eventHandler) {
		System.err.println("WARNING: Dummy EventBroker instance used");
		return true;
	}
}
