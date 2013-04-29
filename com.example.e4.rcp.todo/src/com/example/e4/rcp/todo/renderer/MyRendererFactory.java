package com.example.e4.rcp.todo.renderer;

import org.eclipse.e4.ui.internal.workbench.swt.AbstractPartRenderer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.renderers.swt.StackRenderer;
import org.eclipse.e4.ui.workbench.renderers.swt.WorkbenchRendererFactory;

@SuppressWarnings("restriction")
public class MyRendererFactory extends WorkbenchRendererFactory {

	private boolean useMapRenderer = Boolean
			.getBoolean("MyRendererFactory.useMapRenderer");

	private BrowserRenderer mapRenderer;
	private StackRenderer stackRenderer;

	@Override
	public AbstractPartRenderer getRenderer(MUIElement uiElement, Object parent) {
		if (useMapRenderer && uiElement instanceof MPart) {
			if (mapRenderer == null) {
				mapRenderer = new BrowserRenderer();
				initRenderer(mapRenderer);
			}
			return mapRenderer;
		} else if (uiElement instanceof MPartStack) {
			if (stackRenderer == null) {
				stackRenderer = new MyStackRenderer();
				initRenderer(stackRenderer);
			}
			return stackRenderer;

		}
		return super.getRenderer(uiElement, parent);
	}
}
