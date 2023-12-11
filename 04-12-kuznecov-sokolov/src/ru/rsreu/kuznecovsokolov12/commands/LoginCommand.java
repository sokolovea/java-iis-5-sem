package ru.rsreu.kuznecovsokolov12.commands;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;
import ru.rsreu.kuznecovsokolov12.servlet.EnumLogin;
import ru.rsreu.kuznecovsokolov12.utils.ConfigurationManager;

public class LoginCommand implements ActionCommand {
	public static final int PARAM_MAX_SESSION_LIFE_TIME = 60 * 15;

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
		
		try {
			LoginLogic.setUserAuth(login, true);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(LoginCommand.PARAM_MAX_SESSION_LIFE_TIME);
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
		
		if (loginResult == EnumLogin.ADMIN) {
			return LoginCommand.URL_MAIN_PAGE;
		}
		if (loginResult == EnumLogin.USER || loginResult == EnumLogin.EXPERT) {
			return LoginCommand.URL_MAIN_PAGE;
		}
		
		if (loginResult == EnumLogin.MODERATOR) {
			return LoginCommand.URL_MAIN_PAGE;
		}
		
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
			return ConfigurationManager.getProperty("path.page.admin");
		}
		return LoginCommand.URL_LOGIN_PAGE;
	}
}
