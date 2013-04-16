package com.example.e4.rcp.todo.part;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;

import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class TodoOverview {

	private Label label;
	private TableViewer tv;

	@PostConstruct
	public void createPart(Composite parent, final ITodoService model) {
		GridLayout gl_parent = new GridLayout(2, false);
		gl_parent.horizontalSpacing = 10;
		parent.setLayout(gl_parent);

		Button btnLoadData = new Button(parent, SWT.NONE);
		btnLoadData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				label.setText("Todos #" + model.getTodos().size());
				tv.setInput(model.getTodos());
			}
		});
		btnLoadData.setText("Load Data");
		label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		label.setText("Todos not yet loaded");

		tv = new TableViewer(parent);
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
		tvc.getColumn().setWidth(50);

		// Description
		TableViewerColumn tvc2 = new TableViewerColumn(tv, SWT.NONE);
		tvc2.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				return ((Todo) element).getDescription();
			}
		});
		tvc2.getColumn().setText("Description");
		tvc2.getColumn().setWidth(50);
	}

	@Focus
	public void focus() {
		this.label.setFocus();
	}
}