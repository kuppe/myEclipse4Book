package com.example.e4.rcp.todo.lifecycle;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.dialog.PasswordDialog;

@SuppressWarnings("restriction")
public class LifeCycleManager {

	@PostContextCreate
	public void raisePasswordDialog(IEclipseContext context, IExtensionRegistry registry) throws CoreException {
		context.set(Shell.class, new Shell());
		PasswordDialog d = ContextInjectionFactory.make(PasswordDialog.class, context);
		if (Dialog.OK != d.open()) {
			// Wrong password
			System.exit(-1);
		}

		// Call lifecycle hooks registered via our extension point
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor("com.example.e4.rcp.todo.lifecycleHook");
		for (IConfigurationElement element : elements) {
			final ILifecycleManager extension = (ILifecycleManager) element
					.createExecutableExtension("class");
			// Prevent extension from tearing down the whole runtime
			SafeRunner.run(new SafeRunnable() {
				@Override
				public void run() throws Exception {
					extension.doSomething();
				}
			});
		}
	}
}
