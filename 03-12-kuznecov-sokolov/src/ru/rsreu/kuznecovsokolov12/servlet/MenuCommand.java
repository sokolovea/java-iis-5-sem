package ru.rsreu.kuznecovsokolov12.servlet;

import javax.servlet.http.HttpServletRequest;

public class MenuCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	@Override
	public String execute(HttpServletRequest request) {
		String login = request.getParameter(PARAM_NAME_LOGIN);
		request.getSession().setAttribute("userName", login);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		request.getSession().setAttribute("userPassword", password);
		String destination = request.getParameter("destination");
		EnumLogin loginResult = LoginLogic.checkLogin(login, password);
		request.setAttribute(MenuCommand.getRequestAttribute(loginResult), login);
		return MenuCommand.getPage(loginResult, destination);
	}
	
	private static String getRequestAttribute(EnumLogin loginResult) {
		if (loginResult != EnumLogin.NOUSER) {
			return loginResult.toString();
		}
		return "errorLoginPassMessage";
	}
	
	private static String getPage(EnumLogin loginResult, String destination) {
		if (loginResult == EnumLogin.USER || loginResult == EnumLogin.EXPERT || loginResult == EnumLogin.CAPTAIN) {
			if (destination.equals("team")) {
				return ConfigurationManager.getProperty("path.page.team");
			} else if (destination.equals("main")) {
				return ConfigurationManager.getProperty("path.page.team_select");
			}
		}
		return ConfigurationManager.getProperty("path.page.login");
	}

}
