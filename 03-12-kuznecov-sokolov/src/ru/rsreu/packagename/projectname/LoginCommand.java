package ru.rsreu.packagename.projectname;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		// извлечение из запроса логина и пароля
		String login = request.getParameter(PARAM_NAME_LOGIN);
		String pass = request.getParameter(PARAM_NAME_PASSWORD);
		// проверка логина и пароля
		if (LoginLogic.checkLogin(login, pass)) {
			request.setAttribute("user", login);
			// определение пути к main.jsp
			page = "/jsp/main.jsp";
		} else {
			request.setAttribute("errorLoginPassMessage", "index.jsp");
			page = "/jsp/login.jsp";
		}
		return page;
	}
}
