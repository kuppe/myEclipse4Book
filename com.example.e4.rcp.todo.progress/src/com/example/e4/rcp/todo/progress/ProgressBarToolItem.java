package com.example.e4.rcp.todo.progress;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;

public class ProgressBarToolItem implements IProgressMonitor {
	private volatile boolean cancelled = false;

	@Inject
	private UISynchronize sync;

	private ProgressBar bar;

	@PostConstruct
	public void createControls(Composite parent) {
		bar = new ProgressBar(parent, SWT.SMOOTH);
		bar.setBounds(100, 10, 100, 20);
	}

	@Override
	public void beginTask(final String name, final int totalWork) {
		sync.syncExec(new Runnable() {
			@Override
			public void run() {
				bar.setMaximum(totalWork);
				bar.setToolTipText(name);
			}
		});
	}

	@Override
	public void done() {
		// Explicitly reset cancellation state. This implementation reuses this
		// progress monitor for subsequent jobs which won't run after scheduling
		// if this flag is still marked canceled (left over from the previous
		// job).
		this.cancelled = false;
	}

	@Override
	public void internalWorked(double work) {
	}

	@Override
	public boolean isCanceled() {
		return this.cancelled;
	}

	@Override
	public void setCanceled(boolean value) {
		this.cancelled = value;
	}

	@Override
	public void setTaskName(String name) {
		System.out.println(name);
	}

	@Override
	public void subTask(String name) {
	}

	@Override
	public void worked(final int work) {
		sync.syncExec(new Runnable() {
			@Override
			public void run() {
				bar.setSelection(bar.getSelection() + work);
			}
		});
	}
}