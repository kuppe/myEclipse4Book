package com.example.e4.rcp.todo.handler;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class SaveAllHandler {

	@Execute
	public void execute(EPartService partService) {
		partService.saveAll(true);
	}

	@CanExecute
	public boolean canExecute(EPartService partService) {
		return !partService.getDirtyParts().isEmpty();
	}
}