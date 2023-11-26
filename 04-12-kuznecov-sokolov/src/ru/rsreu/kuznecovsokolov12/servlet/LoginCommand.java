package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
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
			request.getSession().setAttribute("errorLoginPassMessage", "Login or password incorrect");
			page = LoginCommand.getPage(loginResult);
			return page;
		}
		
//		Cookie cookie = new Cookie("model", "Canon D7000");
//		response.addCookie(cookie);
		
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(60);
		session.setAttribute(LoginCommand.PARAM_USER_LOGIN, login);
		session.setAttribute(LoginCommand.PARAM_USER_PASSWORD, password);
		session.setAttribute(LoginCommand.PARAM_USER_ROLE, loginResult);
		
		
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		TeamDAO teamDAO = factory.getTeamDAO();
		UserDAO userDAO = factory.getUserDAO();
		User user = null;
		List<Team> teamList = null;
		try {
			user = userDAO.getUserByLogin(login);
			teamList = teamDAO.getTeamsForUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		factory.returnConnectionToPool();
		if ((teamList != null) && (teamList.size() != 0)) {
			session.setAttribute(LoginCommand.PARAM_TEAM_ID, teamList.get(0).getId());
		}
//		session.setAttribute(LoginCommand.PARAM_TEAM_ID, value);
		
		if (loginResult == EnumLogin.ADMIN) {
			return "/controller?command=menu&destination=main";
//			return MenuCommand.getPage("main", request);
		}
		if (loginResult == EnumLogin.USER || loginResult == EnumLogin.EXPERT) {
			return "/controller?command=menu&destination=main";
//			return MenuCommand.getPage("main", request);
		}
		
		if (loginResult == EnumLogin.MODERATOR) {
			return "/controller?command=menu&destination=main";
//			return MenuCommand.getPage("main", request);
		}
//		request.setAttribute("destination", "main");
//		page = LoginCommand.getPage(loginResult);
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
		return "/controller";//ConfigurationManager.getProperty("path.page.index");
	}
}
