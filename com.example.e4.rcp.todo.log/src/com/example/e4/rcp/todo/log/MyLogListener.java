package com.example.e4.rcp.todo.log;

import org.osgi.framework.Bundle;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;

public class MyLogListener implements LogListener {
	public void setLogReader(LogReaderService logReader) {
		// register for new ones
		logReader.addLogListener(this);
	}

	@Override
	public void logged(LogEntry entry) {
		Bundle bundle = entry.getBundle();
		if (bundle.getSymbolicName().startsWith("com.example.e4")) {
			System.out.println(bundle.getSymbolicName() + " : "
					+ entry.getMessage());
		}
	}
}
