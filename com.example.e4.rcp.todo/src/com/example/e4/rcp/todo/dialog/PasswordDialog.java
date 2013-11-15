package com.example.e4.rcp.todo.dialog;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.core.services.translation.TranslationService;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
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
	@Translation
	private Messages messages;
	
	private IEclipseContext ctx;

	private Label lblPassword;
	private Label lblNewLabel;
	
	private Combo combo;
	
	@Inject
	public PasswordDialog(Shell shell, IEclipseContext ctx) {
		super(shell);
		this.ctx = ctx;
	}

	@Override
	public Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		GridLayout gridLayout = new GridLayout(2, false);
		container.setLayout(gridLayout);

		lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));

		usernameText = new Text(container, SWT.BORDER);
		usernameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		// Without previously stored preferences, username is null
		// which would cause an assertion failure
		if (username != null) {
			usernameText.setText(username);
		}

		lblPassword = new Label(container, SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));

		passwordText = new Text(container, SWT.BORDER | SWT.PASSWORD);
		passwordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		new Label(container, SWT.NONE);
		
		combo = new Combo(container, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo.setItems(new String[] {"en_US", "de_DE"});
		combo.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				ctx.set(TranslationService.LOCALE,
						combo.getItem(combo.getSelectionIndex()));
			}
		});
		combo.select(0);
		
		// see username
		if (password != null) {
			passwordText.setText(password);
		}

		setLanguage(messages);
		
		return container;
	}
	
	@Inject
	private void setLanguage(@Translation Messages messages) {
		if (lblNewLabel != null && !lblNewLabel.isDisposed()) {
			lblNewLabel.setText(messages.PasswordDialog_username);
			lblPassword.setText(messages.PasswordDialog_password);
			lblPassword.getParent().layout();
		}
		if (getButton(IDialogConstants.OK_ID) != null) {
			getButton(IDialogConstants.OK_ID).setText(messages.PasswordDialog_ok);
			getButton(IDialogConstants.CANCEL_ID).setText(messages.PasswordDialog_cancel);
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, messages.PasswordDialog_ok, true);
		createButton(parent, IDialogConstants.CANCEL_ID, messages.PasswordDialog_cancel, false);
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
