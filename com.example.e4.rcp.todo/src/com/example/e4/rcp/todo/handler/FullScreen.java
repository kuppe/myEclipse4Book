 
package com.example.e4.rcp.todo.handler;


import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.prefs.BackingStoreException;

@SuppressWarnings("restriction")
public class FullScreen {
    private static final String FULLSCREEN_MODE = "fullscreenmode";

    @Execute
    public void execute(Shell shell, @Preference IEclipsePreferences prefs) {
        boolean fullscreen = prefs.getBoolean(FULLSCREEN_MODE, false);
        shell.setFullScreen(!fullscreen);
        prefs.putBoolean(FULLSCREEN_MODE, !fullscreen);
        try {
            prefs.flush();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }
}
