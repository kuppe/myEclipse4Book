package com.example.e4.rcp.todo.part;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TodoDetailsPart {
	private Text summary;
	private Text description;
	private Button isDone;
	private DateTime dateTime;

	@PostConstruct
	public void createPartControl(Composite parent) {
		GridLayout gl_parent = new GridLayout(2, false);
		gl_parent.horizontalSpacing = 10;
		parent.setLayout(gl_parent);

		Label lblSummary = new Label(parent, SWT.NONE);
		lblSummary.setText("Summary");

		summary = new Text(parent, SWT.BORDER);
		summary.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Label lblDescription = new Label(parent, SWT.NONE);
		lblDescription.setText("Description");

		description = new Text(parent, SWT.BORDER | SWT.MULTI);
		description.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));

		Label lblDueDate = new Label(parent, SWT.NONE);
		lblDueDate.setText("Due Date");

		dateTime = new DateTime(parent, SWT.BORDER);
		dateTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		new Label(parent, SWT.NONE);

		isDone = new Button(parent, SWT.CHECK);
		isDone.setBounds(0, 0, 115, 24);
		isDone.setText("Done");
	}

	@Focus
	public void focus() {
		this.summary.setFocus();
	}
}