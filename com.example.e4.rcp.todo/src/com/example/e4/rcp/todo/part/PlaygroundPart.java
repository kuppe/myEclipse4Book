package com.example.e4.rcp.todo.part;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class PlaygroundPart {

	private Text text;
	private Browser browser;

	@PostConstruct
	public void createUI(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		text = new Text(parent, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnSearch = new Button(parent, SWT.NONE);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String input = text.getText();
				try {
					browser.setUrl("http://maps.google.de/maps?q="
							+ URLEncoder.encode(input, "UTF-8")
							+ "&output=embed");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSearch.setText("Search");

		browser = new Browser(parent, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
	}

	@Focus
	public void setFocus() {
		// Trigger SWT.Activate
		text.setFocus();
	}
}