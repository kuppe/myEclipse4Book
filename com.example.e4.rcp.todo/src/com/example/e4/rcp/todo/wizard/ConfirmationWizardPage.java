package com.example.e4.rcp.todo.wizard;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ConfirmationWizardPage extends WizardPage implements IWizardPage {

	private Button button;

	public ConfirmationWizardPage(String pageName) {
		super(pageName);
		setTitle("Confirm");
		setDescription("Really create todo?");
	}

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);

		setControl(comp);
		comp.setLayout(new GridLayout(2, false));

		Label lblNewLabel = new Label(comp, SWT.NONE);
		lblNewLabel.setText("Create Todo");

		button = new Button(comp, SWT.CHECK);
		button.setText("check");

		// Tell the wizard dialog to refresh/update its button states
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getWizard().getContainer().updateButtons();
			}
		});
	}

	@Override
	public boolean isPageComplete() {
		return button.getSelection();
	}
}
