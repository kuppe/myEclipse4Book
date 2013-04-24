package com.example.e4.rcp.todo.part;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Composite;
import org.swtchart.Chart;
import org.swtchart.IBarSeries;
import org.swtchart.ISeries.SeriesType;

import com.example.e4.rcp.todo.model.ITodoService;

public class ChartPart {

	private Chart chart;
	private Job j;

	@PostConstruct
	public void postConstruct(Composite parent, final ITodoService model,
			final UISynchronize sync) {
		chart = new Chart(parent, SWT.NONE);

		// set titles
		chart.getTitle().setText("Done Todos/Month");
		chart.getAxisSet().getXAxis(0).getTitle().setText("Month");
		chart.getAxisSet().getYAxis(0).getTitle().setText("Amount Todos");

		// create line series
		final IBarSeries series = (IBarSeries) chart.getSeriesSet()
				.createSeries(SeriesType.BAR, "line series");
		series.setYSeries(model.getStatistics());

		// adjust the axis range
		chart.getAxisSet().adjustRange();

		j = new Job("Updating stats") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Chart refresh job", IProgressMonitor.UNKNOWN);
				while (true) {
					if (monitor.isCanceled()) {
						monitor.done();
						return Status.CANCEL_STATUS;
					}
					final double[] statistics = model.getStatistics();
					sync.syncExec(new Runnable() {
						@Override
						public void run() {
							try {
								// create line series
								series.setYSeries(statistics);

								// force chart to show new stats
								chart.redraw();
							} catch (SWTException e) {
								// widget might already be disposed
								// thus ignore
							}
						}
					});
				}
			}
		};
		j.setSystem(true);
		j.setUser(true);
		j.setPriority(Job.LONG);
	}

	@PreDestroy
	private void stopJob() {
		j.cancel();
	}

	/**
	 * cancels a currently active job or schedules a currently inactive one
	 */
	public void toggleJob() {
		if (j.getState() == Job.RUNNING) {
			j.cancel();
		} else {
			j.schedule();
		}
	}

	@Focus
	public void onFocus() {
		chart.setFocus();
	}
}