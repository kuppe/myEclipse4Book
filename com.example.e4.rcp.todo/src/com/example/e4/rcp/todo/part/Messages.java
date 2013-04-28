package com.example.e4.rcp.todo.part;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.example.e4.rcp.todo.part.messages"; //$NON-NLS-1$
	public static String TodoDetailsPart_lblSummary_text;
	public static String TodoDetailsPart_controlDecoration_descriptionText;
	public static String TodoDetailsPart_lblDescription_text;
	public static String TodoDetailsPart_lblDueDate_text;
	public static String TodoDetailsPart_isDone_text;
	public static String TodoOverview_btnLoadedData_text;
	public static String TodoOverview_btnSuccessLoadData_text;
	public static String TodoOverview_btnLoadData_text;
	public static String TodoOverview_label_text;
	public static String TodoOverview_other_text;
	public static String TodoOverview_other_text_1;

	// //////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	// //////////////////////////////////////////////////////////////////////////
	private Messages() {
		// do not instantiate
	}

	// //////////////////////////////////////////////////////////////////////////
	//
	// Class initialization
	//
	// //////////////////////////////////////////////////////////////////////////
	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
