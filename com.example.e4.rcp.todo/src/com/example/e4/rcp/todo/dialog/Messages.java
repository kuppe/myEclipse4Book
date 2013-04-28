package com.example.e4.rcp.todo.dialog;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.example.e4.rcp.todo.dialog.messages"; //$NON-NLS-1$
	public static String PasswordDialog_cancel;
	public static String PasswordDialog_ok;
	public static String PasswordDialog_password;
	public static String PasswordDialog_username;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
