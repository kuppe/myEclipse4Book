package com.example.e4.rcp.todo.part;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.example.e4.rcp.todo.model.ITodoService;

public class TodoOverview {

	private Label label;

	@PostConstruct
	public void createPart(Composite parent, final ITodoService model) {
		GridLayout gl_parent = new GridLayout(3, false);
		gl_parent.horizontalSpacing = 10;
		parent.setLayout(gl_parent);

		Button btnLoadData = new Button(parent, SWT.NONE);
		btnLoadData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				label.setText("Todos #" + model.getTodos().size());
			}
		});
		btnLoadData.setText("Load Data");
		label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		label.setText("Todos not yet loaded");
		new Label(parent, SWT.NONE);
	}

	@Focus
	public void focus() {
		this.label.setFocus();
	}
}