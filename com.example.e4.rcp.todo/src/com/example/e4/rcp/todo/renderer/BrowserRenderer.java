package com.example.e4.rcp.todo.renderer;

import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.workbench.renderers.swt.SWTPartRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("restriction")
public class BrowserRenderer extends SWTPartRenderer {

	@Override
	public Object createWidget(MUIElement element, Object parent) {
		Composite mapComposite = new Composite((Composite) parent, SWT.NONE);
		mapComposite.setLayout(new GridLayout(1, false));
		Browser browser = new Browser(mapComposite, SWT.NONE);
		browser.setUrl("http://www.vogella.com");
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		return mapComposite;
	}
}
