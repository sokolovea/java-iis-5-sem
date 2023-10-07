package ru.rsreu.packagename.projectname;

import java.util.ResourceBundle;

public class MessageManager {
	private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.message");

	private MessageManager() {
	}

	public static String getProperty(String key) {
		return resourceBundle.getString(key);
	}
}