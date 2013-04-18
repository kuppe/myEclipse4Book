package com.example.e4.rcp.todo.wizard;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class TodoWizard extends Wizard {

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
