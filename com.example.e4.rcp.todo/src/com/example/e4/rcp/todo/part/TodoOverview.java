package com.example.e4.rcp.todo.part;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class TodoOverview {

	private Label label;
	private TableViewer tv;
	private Text text;

	private static class MyViewerFilter extends ViewerFilter {
		private String filter;

		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			Todo t = (Todo) element;
			if (t.getSummary().startsWith(filter)) {
				return true;
			}
			return false;
		}

		public void setFilter(String filter) {
			this.filter = filter;
		}
	}

	@PostConstruct
	public void createPart(final Composite parent, final ITodoService model) {
		GridLayout gl_parent = new GridLayout(2, false);
		gl_parent.horizontalSpacing = 10;
		parent.setLayout(gl_parent);

		final Button btnLoadData = new Button(parent, SWT.NONE);
		btnLoadData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				label.setText("Todos #" + model.getTodos().size());
				tv.setInput(model.getTodos());

				btnLoadData.setText("Model Loaded successfully");
				parent.layout();
			}
		});
		btnLoadData.setText("Load Data");
		label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		label.setText("Todos not yet loaded");

		text = new Text(parent, SWT.BORDER | SWT.H_SCROLL | SWT.SEARCH
				| SWT.ICON_SEARCH | SWT.CANCEL);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		text.setMessage("Filter");
		text.addModifyListener(new ModifyListener() {
			private final MyViewerFilter viewerFilter = new MyViewerFilter();

			@Override
			public void modifyText(ModifyEvent e) {
				Text text = (Text) e.getSource();
				final String filter = text.getText();
				if (filter != null && !filter.equals("")) {
					tv.removeFilter(viewerFilter);
					viewerFilter.setFilter(filter);
					tv.addFilter(viewerFilter);
				} else if (filter != null && filter.equals("")) {
					tv.removeFilter(viewerFilter);
				}
			}
		});

		Composite tableComposite = new Composite(parent, SWT.NONE);
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 2, 1));
		TableColumnLayout layout = new TableColumnLayout();
		tableComposite.setLayout(layout);

		tv = new TableViewer(tableComposite);
		Table table = tv.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		tv.setContentProvider(ArrayContentProvider.getInstance());

		// Summary
		TableViewerColumn tvc = new TableViewerColumn(tv, SWT.NONE);
		tvc.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Todo) element).getSummary();
			}
		});
		tvc.getColumn().setText("Summary");
		layout.setColumnData(tvc.getColumn(), new ColumnWeightData(40));

		// Description
		TableViewerColumn tvc2 = new TableViewerColumn(tv, SWT.NONE);
		tvc2.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				return ((Todo) element).getDescription();
			}
		});
		tvc2.getColumn().setText("Description");
		layout.setColumnData(tvc2.getColumn(), new ColumnWeightData(60));
	}

	@Focus
	public void focus() {
		this.label.setFocus();
	}
}