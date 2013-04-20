package com.example.e4.rcp.todo.dialog;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.prefs.BackingStoreException;

@SuppressWarnings("restriction")
public class PasswordDialog extends Dialog {
	
	public static final String NODEPATH = "com.example.e4.rcp.todo";
	
	private static final String PASSWORD = "password";
	
	private static final String USERNAME = "username";

	private Text usernameText;

	private Text passwordText;

	@Inject
	@Preference(nodePath = NODEPATH, value = USERNAME)
	private String username;

	@Inject
	@Preference(nodePath = NODEPATH, value = PASSWORD)
	private String password;

	@Inject
	@Preference(nodePath = NODEPATH)
	private IEclipsePreferences preferences;
	
	@Inject
	public PasswordDialog(Shell shell) {
		super(shell);
	}

	@Override
	public Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		GridLayout gridLayout = new GridLayout(2, false);
		container.setLayout(gridLayout);

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel.setText("Username");

		usernameText = new Text(container, SWT.BORDER);
		usernameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		// Without previously stored preferences, username is null
		// which would cause an assertion failure
		if (username != null) {
			usernameText.setText(username);
		}

		Label lblPassword = new Label(container, SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblPassword.setText("Password");

		passwordText = new Text(container, SWT.BORDER | SWT.PASSWORD);
		passwordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		// see username
		if (password != null) {
			passwordText.setText(password);
		}

		return container;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "OK", true);
		createButton(parent, IDialogConstants.CANCEL_ID, "Cancel", false);
	}

	@Override
	protected void okPressed() {
		preferences.put(USERNAME, usernameText.getText());
		preferences.put(PASSWORD, passwordText.getText());

		try {
			preferences.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		
		super.okPressed();
	}
}
