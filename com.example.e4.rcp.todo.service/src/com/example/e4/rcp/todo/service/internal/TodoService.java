package com.example.e4.rcp.todo.service.internal;

import com.example.e4.rcp.todo.model.ITodoService;

public class TodoService {
	private static ITodoService model = new MyTodoServiceImpl();

	public static ITodoService getInstance() {
		return model;
	}
}
