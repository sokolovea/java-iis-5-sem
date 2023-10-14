package ru.rsreu.kuznecovsokolov12.servlet;

import javax.servlet.http.HttpServletRequest;

public class ReportCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	@Override
	public String execute(HttpServletRequest request) {
		String login = request.getParameter(PARAM_NAME_LOGIN);
		EnumLogin loginResult = LoginLogic.checkLogin(login, "1"); //bad code
		request.setAttribute(ReportCommand.getRequestAttribute(loginResult), login);
//		return ReportCommand.getPage(loginResult);
		return ReportCommand.getPage(EnumLogin.USER);
	}
	
	private static String getRequestAttribute(EnumLogin loginResult) {
		if (loginResult != EnumLogin.NOUSER) {
			return loginResult.toString();
		}
		return "errorLoginPassMessage";
	}
	
	private static String getPage(EnumLogin loginResult) {
		if (loginResult == EnumLogin.USER || loginResult == EnumLogin.EXPERT || loginResult == EnumLogin.CAPTAIN) {
			return ConfigurationManager.getProperty("path.page.reports");
		}
		return ConfigurationManager.getProperty("path.page.login");
	}

}
