package com.example.e4.rcp.todo.part;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.example.e4.rcp.todo.model.Todo;

public class TodoDetailsPart {
	private Text summary;
	private Text description;
	private Button isDone;
	private DateTime dateTime;
	private Todo todo;

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

		Image image = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_INFORMATION)
				.getImage();

		ControlDecoration controlDecoration = new ControlDecoration(summary,
				SWT.RIGHT | SWT.TOP);
		controlDecoration.setShowOnlyOnFocus(true);
		controlDecoration
				.setDescriptionText("Use CTRL+SPACE to see possible values");
		controlDecoration.setImage(image);

		try {
			new ContentProposalAdapter(summary, new TextContentAdapter(),
					new SimpleContentProposalProvider(new String[] {
							"Summary1", "Summary2" }),
					KeyStroke.getInstance("Ctrl+Space"), null);
		} catch (ParseException e) {
			e.printStackTrace();
		}

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

	@Inject
	public void setTodo(
			@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Todo todo) {
		if (todo != null) {
			this.todo = todo;
			updateUserInterface(todo);
		}
	}

	private void updateUserInterface(Todo todo) {
		if (this.summary != null && !this.summary.isDisposed()) {
			if (todo == null) {
				return;
			}
		}
	}

	@Focus
	public void focus() {
		this.summary.setFocus();
	}
}