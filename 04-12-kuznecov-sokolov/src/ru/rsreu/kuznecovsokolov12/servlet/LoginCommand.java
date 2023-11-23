package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class LoginCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String login = request.getParameter(LoginCommand.PARAM_USER_LOGIN);
		String password = request.getParameter(LoginCommand.PARAM_USER_PASSWORD);
		
		EnumLogin loginResult = null;
		try {
			loginResult = LoginLogic.checkLogin(login, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (!LoginCommand.isRoleExist(loginResult)) {
			request.setAttribute("errorLoginPassMessage", "Login or password incorrect");
			page = LoginCommand.getPage(loginResult);
			return page;
		}
		
		HttpSession session = request.getSession(true);
		session.setAttribute(LoginCommand.PARAM_USER_LOGIN, login);
		session.setAttribute(LoginCommand.PARAM_USER_PASSWORD, password);
		session.setAttribute(LoginCommand.PARAM_USER_ROLE, loginResult);
		
		if (loginResult == EnumLogin.ADMIN) {
			return MenuCommand.getPage("main", request);
		}
		if (loginResult == EnumLogin.USER || loginResult == EnumLogin.EXPERT) {
			return MenuCommand.getPage("main", request);
		}
		
		if (loginResult == EnumLogin.MODERATOR) {
			return MenuCommand.getPage("main", request);
		}
//		request.setAttribute("destination", "main");
		page = LoginCommand.getPage(loginResult);
		return page;
	}
	
	private static boolean isRoleExist(EnumLogin loginResult) {
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
