import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

//Send questions, comments, bug reports, etc. to the authors:

//Rob Warner (rwarner@interspatial.com)
//Robert Harris (rbrt_harris@yahoo.com)

/**
 * This program demonstrates BusyIndicator
 */
public class BusyIndicatorTest {
	// The amount of time to sleep (in ms)
	private static final int SLEEP_TIME = 3000;

	// Labels for the button
	private static final String RUN = "Press to Run";
	private static final String IS_RUNNING = "Running...";

	/**
	 * Runs the application
	 */
	private void run() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("BusyIndicator Test");
		createContents(shell);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	/**
	 * Create the window's contents
	 * 
	 * @param shell
	 *            the parent shell
	 */
	private void createContents(final Shell shell) {
		shell.setLayout(new FillLayout());
		final Button button = new Button(shell, SWT.PUSH);
		button.setText(RUN);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// Change the button's text
				button.setText(IS_RUNNING);
				button.update();

				// Show the busy indicator
				BusyIndicator.showWhile(button.getDisplay(), new SleepThread(
						SLEEP_TIME));

				// ProgressMonitorDialog dialog = new
				// ProgressMonitorDialog(shell);
				// try {
				// dialog.run(true, true, new IRunnableWithProgress() {
				//
				// @Override
				// public void run(IProgressMonitor monitor)
				// throws InvocationTargetException,
				// InterruptedException {
				//
				// int cycles = 999999999;
				//
				// monitor.beginTask(
				// "Sum up the numbers from 0 to 999...",
				// cycles);
				//
				// // Poor mans version of .5*n*(n+1)
				// int j = 0;
				// for (int i = 0; i < cycles; i++) {
				// if (monitor.isCanceled()) {
				// break;
				// } else {
				// j += i;
				// monitor.worked(1);
				// }
				// }
				//
				// monitor.done();
				//
				// System.out.println(j);
				// }
				// });
				// } catch (InvocationTargetException e) {
				// e.printStackTrace();
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }

				// Thread has completed; reset the button's text
				button.setText(RUN);
			}
		});
	}

	/**
	 * Application's entry point
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		new BusyIndicatorTest().run();
	}
}

/**
 * This class is a thread that sleeps the specified number of milliseconds
 */

class SleepThread extends Thread {
	private long ms;

	/**
	 * SleepThread constructor
	 * 
	 * @param ms
	 *            the number of milliseconds to sleep
	 */
	public SleepThread(long ms) {
		this.ms = ms;
	}

	/**
	 * Runs the thread
	 */
	public void run() {
		try {
			sleep(ms);
		} catch (InterruptedException e) {
		}
	}
}
