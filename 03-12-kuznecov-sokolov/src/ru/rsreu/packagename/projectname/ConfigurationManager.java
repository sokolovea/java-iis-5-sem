package ru.rsreu.packagename.projectname;

import java.util.ResourceBundle;

public class ConfigurationManager {
	
	private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("configuration");
	private ConfigurationManager() {
	}

	public static String getProperty(String key) {
		return resourceBundle.getString(key);
	}
}
