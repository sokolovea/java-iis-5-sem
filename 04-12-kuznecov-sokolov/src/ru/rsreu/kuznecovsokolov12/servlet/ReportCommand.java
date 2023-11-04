package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class ReportCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	
	@Override
	public String execute(HttpServletRequest request) {
		String login = request.getParameter(PARAM_NAME_LOGIN);
		request.getSession().setAttribute("userName", login);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		request.getSession().setAttribute("userPassword", password);
		EnumLogin loginResult = null;
		try {
			loginResult = LoginLogic.checkLogin(login, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute(ReportCommand.getRequestAttribute(loginResult), login);
		return ReportCommand.getPage(loginResult);
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
