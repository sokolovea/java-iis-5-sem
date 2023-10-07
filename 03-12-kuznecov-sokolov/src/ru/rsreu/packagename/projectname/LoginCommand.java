package ru.rsreu.packagename.projectname;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String login = request.getParameter(PARAM_NAME_LOGIN);
		String pass = request.getParameter(PARAM_NAME_PASSWORD);
		if (LoginLogic.checkLogin(login, pass)) {
			request.setAttribute("user", login);
			page = "/jsp/main.jsp";
		} else {
			request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("path.page.login"));
			page = "/jsp/login.jsp";
		}
		return page;
	}
}
