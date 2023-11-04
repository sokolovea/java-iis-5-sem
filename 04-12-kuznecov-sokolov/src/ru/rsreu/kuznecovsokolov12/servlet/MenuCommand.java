package ru.rsreu.kuznecovsokolov12.servlet;

import javax.servlet.http.HttpServletRequest;

public class MenuCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final String PARAM_USER_NAME = "userName";
	private static final String PARAM_USER_PASSWORD = "userPassword";
	private static final String PARAM_DESTINATION = "destination";
	@Override
	public String execute(HttpServletRequest request) {
		String login = request.getParameter(PARAM_NAME_LOGIN);
		request.getSession().setAttribute(MenuCommand.PARAM_USER_NAME, login);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		request.getSession().setAttribute(MenuCommand.PARAM_USER_PASSWORD, password);
		String destination = request.getParameter(MenuCommand.PARAM_DESTINATION);
		EnumLogin loginResult = LoginLogic.checkLogin(login, password);
		return MenuCommand.getPage(loginResult, destination);
	}
	
//	private static String getRequestAttribute(EnumLogin loginResult) {
//		if (loginResult != EnumLogin.NOUSER) {
//			return loginResult.toString();
//		}
//		return "errorLoginPassMessage";
//	}
	
	private static String getPage(EnumLogin loginResult, String destination) {
		if (loginResult == EnumLogin.USER || loginResult == EnumLogin.EXPERT || loginResult == EnumLogin.CAPTAIN) {
			if (destination.equals("team")) {
				return ConfigurationManager.getProperty("path.page.team");
			} else if (destination.equals("main")) {
				return ConfigurationManager.getProperty("path.page.team_select");
			}
		}
		if (loginResult == EnumLogin.MODERATOR) {
			if (destination.equals("main")) {
				return ConfigurationManager.getProperty("path.page.moderator");
			}
		}
		if (loginResult == EnumLogin.ADMIN ) {
			if (destination.equals("settings")) {
				return ConfigurationManager.getProperty("path.page.admin_settings");
			} else if (destination.equals("main")) {
				return ConfigurationManager.getProperty("path.page.admin");
			}
		}
		return ConfigurationManager.getProperty("path.page.login");
	}

}
