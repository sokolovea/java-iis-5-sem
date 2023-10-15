package ru.rsreu.kuznecovsokolov12.servlet;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String login = request.getParameter(PARAM_NAME_LOGIN);
		String pass = request.getParameter(PARAM_NAME_PASSWORD);
		
		EnumLogin loginResult = LoginLogic.checkLogin(login, pass);
		request.setAttribute("userName", login);
		request.setAttribute("userPassword", pass);
		page = LoginCommand.getPage(loginResult);
		System.out.println(ru.rsreu.kuznecovsokolov12.servlet.EnumLogin.ADMIN.toString());
		return page;
	}
	
	private static String getRequestAttribute(EnumLogin loginResult) {
		if (loginResult != EnumLogin.NOUSER) {
			return loginResult.toString();
		}
		return "errorLoginPassMessage";
	}
	
	private static String getPage(EnumLogin loginResult) {
		if (loginResult == EnumLogin.USER || loginResult == EnumLogin.EXPERT || loginResult == EnumLogin.CAPTAIN) {
			return ConfigurationManager.getProperty("path.page.team_select");
		}
		return ConfigurationManager.getProperty("path.page.login");
	}
}
