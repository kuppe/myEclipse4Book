package com.example.e4.rcp.todo.wizard;

import javax.inject.Inject;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import com.example.e4.rcp.todo.model.Todo;

public class TodoWizard extends Wizard {

	@Inject
	private Todo todo;

	public TodoWizard() {
		setWindowTitle("New Todo Window Title");
	}

	@Override
	public void addPages() {
		addPage(new TodoWizardPage("New Todo", todo));
		addPage(new ConfirmationWizardPage("Confirm Todo"));
		super.addPages();
	}

	@Override
	public boolean canFinish() {
		// Iterate all pages and find out if they allow us to finish
		IWizardPage[] wizardPages = getPages();
		for (IWizardPage iWizardPage : wizardPages) {
			if (!iWizardPage.isPageComplete()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean performFinish() {
		return true;
	}
}
