package com.example.e4.rcp.todo.part;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
	public void postConstruct(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		Combo combo = new Combo(parent, SWT.NONE);
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
		comboViewer.setInput(model.getTodos());
		comboViewer.setSelection(new StructuredSelection(model.getTodos()
				.get(0)));

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
						model.deleteTodo(((Todo) element).getId());
						comboViewer.setInput(model.getTodos());
						if (model.getTodos().size() > 0) {
							comboViewer.setSelection(new StructuredSelection(
									model.getTodos().get(0)));
						} else {
							button.setEnabled(false);
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