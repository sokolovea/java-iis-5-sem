package ru.rsreu.packagename.projectname;

import java.util.ResourceBundle;

public class ConfigurationManager {
	
	private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("");
	private ConfigurationManager() {
	}

	public static String getProperty(String key) {
		return "/jsp/index.jsp";
	}
}
