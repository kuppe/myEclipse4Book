package com.example.e4.rcp.todo.service.internal;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;

import com.example.e4.rcp.todo.model.ITodoService;

@SuppressWarnings("restriction")
public class TodoServiceContextFunction extends ContextFunction {

	@Override
	public Object compute(IEclipseContext context, String contextKey) {
		MyTodoServiceImpl service = ContextInjectionFactory.make(
				MyTodoServiceImpl.class, context);

		// put into app context
		MApplication application = context.get(MApplication.class);
		IEclipseContext ctx = application.getContext();
		ctx.set(ITodoService.class, service);

		return service;
	}
}
