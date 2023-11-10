package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class DatabaseCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	
	@Override
	public String execute(HttpServletRequest request) {
		String login = request.getParameter(PARAM_NAME_LOGIN);
		request.getSession().setAttribute("userName", login);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		request.getSession().setAttribute("userPassword", password);
		String key1 = request.getParameter("key1");
		String key2 = request.getParameter("key2");
		String v1 = request.getParameter("teamCapacity");
		System.out.println(login + "; " + password + "; " +key1 + "; " + key2 + "; " + v1);
		
		EnumLogin loginResult = null;
		try {
			loginResult = LoginLogic.checkLogin(login, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute(DatabaseCommand.getRequestAttribute(loginResult), login);
		return DatabaseCommand.getPage(loginResult);
	}
	
	private static String getRequestAttribute(EnumLogin loginResult) {
		if (loginResult != EnumLogin.NOUSER) {
			return loginResult.toString();
		}
		return "errorLoginPassMessage";
	}
	
	private static String getPage(EnumLogin loginResult) {
		if (loginResult != EnumLogin.NOUSER) {
			return ConfigurationManager.getProperty("path.page.reports");
		}
		return ConfigurationManager.getProperty("path.page.login");
	}

}
