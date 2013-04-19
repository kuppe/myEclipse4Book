package com.example.e4.rcp.todo.part;

import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
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

@SuppressWarnings("restriction")
public class TodoOverview {

	private Label label;
	private TableViewer tv;
	private Text text;
	private WritableList withElementType;

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
	public void createPart(final Composite parent, final ITodoService model,
			EMenuService menuService, final ESelectionService selectionService,
			final UISynchronize sync) {
		GridLayout gl_parent = new GridLayout(2, false);
		gl_parent.horizontalSpacing = 10;
		parent.setLayout(gl_parent);

		final Button btnLoadData = new Button(parent, SWT.NONE);
		btnLoadData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Job job = new Job("Model loader...") {
					@Override
					protected IStatus run(IProgressMonitor monitor) {
						final List<Todo> todos = model.getTodos();
						final int size = todos.size();
						sync.asyncExec(new Runnable() {
							@Override
							public void run() {
								label.setText("Todos #" + size);

								btnLoadData
										.setText("Model Loaded successfully");
								parent.layout();

								withElementType.clear();
								withElementType.addAll(todos);
							}
						});
						return Status.OK_STATUS;
					}
				};
				job.schedule();
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

		tv = new TableViewer(tableComposite, SWT.SINGLE);
		Table table = tv.getTable();
		tv = new TableViewer(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		// Summary
		TableViewerColumn tvc = new TableViewerColumn(tv, SWT.NONE);
		tvc.getColumn().setText("Summary");
		layout.setColumnData(tvc.getColumn(), new ColumnWeightData(40));
		tvc.setEditingSupport(new EditingSupport(tv) {
			private TextCellEditor textCellEditor = new TextCellEditor(tv
					.getTable());;

			@Override
			protected void setValue(Object element, Object value) {
				Todo todo = (Todo) element;
				todo.setSummary((String) value);
				tv.refresh();
			}

			@Override
			protected Object getValue(Object element) {
				Todo todo = (Todo) element;
				return todo.getSummary();
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return textCellEditor;
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});

		// Description
		TableViewerColumn tvc2 = new TableViewerColumn(tv, SWT.NONE);
		tvc2.getColumn().setText("Description");
		layout.setColumnData(tvc2.getColumn(), new ColumnWeightData(60));

		IValueProperty[] labelProperties = BeanProperties.values(new String[] {
				Todo.SUMMARY, Todo.DESCRIPTION });
		withElementType = WritableList.withElementType(Todo.class);
		ViewerSupport.bind(tv, withElementType, labelProperties);

		tv.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				// Only send Todo instead of IStructuredSelection
				// Table is SWT.SINGLE
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				selectionService.setSelection(selection.getFirstElement());
			}
		});

		menuService.registerContextMenu(tv.getTable(),
				"com.example.e4.rcp.todo.popupmenu.table");
	}

	@Focus
	public void focus() {
		this.label.setFocus();
	}
}