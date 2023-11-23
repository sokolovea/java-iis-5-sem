package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {
		public static final String PARAM_USER_LOGIN = "login";
		public static final String PARAM_USER_PASSWORD = "password";
		public static final String PARAM_USER_ROLE = "role";
		public static final String PARAM_DESTINATION = "destination";
		public static final String PARAM_ACTIVITY = "activity";
		
	String execute(HttpServletRequest request);
}
