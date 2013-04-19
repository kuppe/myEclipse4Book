package com.example.e4.rcp.todo.part;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class TodoDeletionPart {

	@Inject
	private ITodoService model;

	private ComboViewer comboViewer;

	@PostConstruct
	public void postConstruct(final Composite parent, final UISynchronize sync) {
		parent.setLayout(new GridLayout(2, false));

		Combo combo = new Combo(parent, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		comboViewer = new ComboViewer(combo);
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Todo) {
					return ((Todo) element).getSummary();
				}
				return super.getText(element);
			}
		});

		Job j = new Job("Loading todos for deletion part") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				final List<Todo> todos = model.getTodos();
				sync.asyncExec(new Runnable() {
					@Override
					public void run() {
						comboViewer.setInput(todos);
						comboViewer.setSelection(new StructuredSelection(todos
								.get(0)));
						parent.layout();
					}
				});

				return Status.OK_STATUS;
			}
		};
		j.schedule();

		final Button button = new Button(parent, SWT.PUSH);
		button.setText("Delete Todo");
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = comboViewer.getSelection();
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection iss = (IStructuredSelection) selection;
					Object element = iss.getFirstElement();
					if (element instanceof Todo) {
						Todo todo = (Todo) element;
						model.deleteTodo(todo.getId());
						comboViewer.remove(todo);
						int itemCount = comboViewer.getCombo().getItemCount();
						if (itemCount == 0) {
							button.setEnabled(false);
						} else {
							comboViewer.setSelection(new StructuredSelection(
									comboViewer.getElementAt(0)));
						}
					}
				}
				super.widgetSelected(e);
			}
		});
	}

	@Focus
	public void onFocus() {
		this.comboViewer.getControl().setFocus();
	}
}