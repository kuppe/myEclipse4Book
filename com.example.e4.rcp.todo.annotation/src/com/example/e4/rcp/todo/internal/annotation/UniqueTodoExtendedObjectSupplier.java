package com.example.e4.rcp.todo.internal.annotation;

import java.util.Date;

import org.eclipse.e4.core.di.suppliers.ExtendedObjectSupplier;
import org.eclipse.e4.core.di.suppliers.IObjectDescriptor;
import org.eclipse.e4.core.di.suppliers.IRequestor;

import com.example.e4.rcp.todo.model.Todo;

@SuppressWarnings("restriction")
public class UniqueTodoExtendedObjectSupplier extends ExtendedObjectSupplier {

	@Override
	public Object get(IObjectDescriptor descriptor, IRequestor requestor,
			boolean track, boolean group) {
		// TODO Have MyTodoServiceImpl handle Todos marked with -1L
		return new Todo(-1L, "Summary (template)", "Description (template)",
				false, new Date());
	}
}
