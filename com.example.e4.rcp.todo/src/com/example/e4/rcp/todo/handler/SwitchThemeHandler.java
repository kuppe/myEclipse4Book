package com.example.e4.rcp.todo.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;

@SuppressWarnings("restriction")
public class SwitchThemeHandler {
	private static final String DARK = "com.example.e4.rcp.todo.theme.dark";
	private static final String RED = "com.example.e4.rcp.todo.theme.red";

	@Execute
	public void execute(IThemeEngine engine) {
		String id = engine.getActiveTheme().getId();
		if (DARK.equals(id)) {
			engine.setTheme(RED, true);
		} else {
			engine.setTheme(DARK, true);
		}
	}
}