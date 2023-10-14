package ru.rsreu.kuznecovsokolov12.servlet;

import javax.servlet.http.HttpServletRequest;

public class ReportCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_ROLE = "role";
	@Override
	public String execute(HttpServletRequest request) {
		String login = request.getParameter(PARAM_NAME_LOGIN);
		request.getSession().setAttribute("userName", login);
		String role = request.getParameter(PARAM_NAME_ROLE);
		request.getSession().setAttribute("userRole", role);
//		request.getSession().setAttribute("userName", request.getSession().getAttribute("login"));
//		login = (String) request.getAttribute("user");
//		EnumLogin loginResult = LoginLogic.checkLogin(login, "1"); //bad code
//		request.setAttribute(ReportCommand.getRequestAttribute(loginResult), login);
		System.out.println(login);
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
