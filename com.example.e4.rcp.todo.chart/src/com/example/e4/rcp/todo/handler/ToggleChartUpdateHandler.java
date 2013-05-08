package com.example.e4.rcp.todo.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import com.example.e4.rcp.todo.part.ChartPart;

public class ToggleChartUpdateHandler {

	@Execute
	public void execute(MPart part) {
		ChartPart chartPart = (ChartPart) part.getObject();
		chartPart.toggleJob();
	}
}
