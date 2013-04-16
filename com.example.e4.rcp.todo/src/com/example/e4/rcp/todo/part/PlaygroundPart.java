package com.example.e4.rcp.todo.part;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class PlaygroundPart {

	private Label label;

	@PostConstruct
	public void createUI(Composite parent) {
		label = new Label(parent, SWT.NONE);
		label.setText("A Label");
	}

	@Focus
	public void setFocus() {
		// Trigger SWT.Activate
		label.setFocus();
	}
}