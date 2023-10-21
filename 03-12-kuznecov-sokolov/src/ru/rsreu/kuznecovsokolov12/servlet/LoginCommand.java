package ru.rsreu.kuznecovsokolov12.servlet;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final String PARAM_USER_NAME = "userName";
	private static final String PARAM_USER_PASSWORD = "userPassword";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String login = request.getParameter(LoginCommand.PARAM_NAME_LOGIN);
		String pass = request.getParameter(LoginCommand.PARAM_NAME_PASSWORD);
		
		EnumLogin loginResult = LoginLogic.checkLogin(login, pass);
		request.setAttribute(LoginCommand.PARAM_USER_NAME, login);
		request.setAttribute(LoginCommand.PARAM_USER_PASSWORD, pass);
		if (!LoginCommand.isLogined(loginResult)) {
			request.setAttribute("errorLoginPassMessage", "Login or password incorrect");
		}
		page = LoginCommand.getPage(loginResult);
		return page;
	}
	
	private static boolean isLogined(EnumLogin loginResult) {
		return (loginResult != EnumLogin.NOUSER);
	}
	
	private static String getPage(EnumLogin loginResult) {
		if (loginResult == EnumLogin.USER || loginResult == EnumLogin.EXPERT || loginResult == EnumLogin.CAPTAIN) {
			return ConfigurationManager.getProperty("path.page.team_select");
		}
		if (loginResult == EnumLogin.MODERATOR) {
			return ConfigurationManager.getProperty("path.page.moderator");
		}
		if (loginResult == EnumLogin.ADMIN) {
			return ConfigurationManager.getProperty("path.page.administrator");
		}
		return ConfigurationManager.getProperty("path.page.login");
	}
}
