package com.example.e4.rcp.todo.part;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class DynamicPart {

	private Label label;

	@PostConstruct
	public void createUI(Composite parent) {
		label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		label.setText("Dynamic part works!!!");
	}

	@Focus
	public void setFocus() {
		label.setFocus();
	}
}
