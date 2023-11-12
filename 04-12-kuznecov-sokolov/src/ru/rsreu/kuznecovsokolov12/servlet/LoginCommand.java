package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

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
		
		EnumLogin loginResult = null;
		try {
			loginResult = LoginLogic.checkLogin(login, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute(LoginCommand.PARAM_USER_NAME, login);
		request.setAttribute(LoginCommand.PARAM_USER_PASSWORD, pass);
		if (!LoginCommand.isLogined(loginResult)) {
			request.setAttribute("errorLoginPassMessage", "Login or password incorrect");
		}
		
		if (loginResult == EnumLogin.ADMIN) {
			return MenuCommand.getPage(loginResult, "main", request);
		}
		if (loginResult == EnumLogin.USER || loginResult == EnumLogin.EXPERT) {
			return MenuCommand.getPage(loginResult, "main", request);
		}
		
		if (loginResult == EnumLogin.MODERATOR) {
			return MenuCommand.getPage(loginResult, "main", request);
		}
//		request.setAttribute("destination", "main");
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
			String destination = "main";
			return ConfigurationManager.getProperty("path.page.admin");
		}
		return ConfigurationManager.getProperty("path.page.login");
	}
}
