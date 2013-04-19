package com.example.e4.rcp.todo.service.internal;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import com.example.e4.rcp.todo.model.ITodoService;

public class TodoServiceContextFunction extends ContextFunction {

	@Override
	public Object compute(IEclipseContext context, String contextKey) {
		MyTodoServiceImpl service = ContextInjectionFactory.make(
				MyTodoServiceImpl.class, context);

		// Register as OSGi service
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		BundleContext bundleContext = bundle.getBundleContext();
		bundleContext.registerService(ITodoService.class, service, null);

		return service;
	}
}
