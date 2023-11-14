package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.DeletedMessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageAttachingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.DeletedMessage;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.MessageAttaching;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Setting;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;
import test.RedirectErrorPage;

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
		EnumLogin loginResult = null;
		try {
			loginResult = LoginLogic.checkLogin(login, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return MenuCommand.getPage(loginResult, destination, request);
	}
	
//	private static String getRequestAttribute(EnumLogin loginResult) {
//		if (loginResult != EnumLogin.NOUSER) {
//			return loginResult.toString();
//		}
//		return "errorLoginPassMessage";
//	}
	
	public static String getPage(EnumLogin loginResult, String destination, HttpServletRequest request) {
		if (loginResult == EnumLogin.USER || loginResult == EnumLogin.EXPERT || loginResult == EnumLogin.CAPTAIN) {
			if (destination.equals("team")) {
				try {
					MenuCommand.fillTeamPageForUser(request);
				}
				catch (RedirectErrorPage e) {
					return ConfigurationManager.getProperty("path.page.error");
				}
				return ConfigurationManager.getProperty("path.page.team");
			} else if (destination.equals("main")) {
				MenuCommand.fillTeamSelectPageForUser(request);
				return ConfigurationManager.getProperty("path.page.team_select");
			}
		}
		if (loginResult == EnumLogin.MODERATOR) {
			if (destination.equals("main")) {
				MenuCommand.fillMainPageForModerator(request);
				return ConfigurationManager.getProperty("path.page.moderator");
			}
		}
		if (loginResult == EnumLogin.ADMIN ) {
			if (destination.equals("settings")) {
				MenuCommand.fillSettingsPageForAdmin(request);
				return ConfigurationManager.getProperty("path.page.admin_settings");
			} else if (destination.equals("main")) {
				MenuCommand.fillMainPageForAdmin(request);
				return ConfigurationManager.getProperty("path.page.admin");
			}
		}
		return ConfigurationManager.getProperty("path.page.login");
	}
	
	private static void fillSettingsPageForAdmin(HttpServletRequest request) {
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		SettingDAO settingDAO = factory.getSettingDAO();
		try {
			List<Setting> settingList = settingDAO.getSetting();
			request.setAttribute("setting_list", settingList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		factory.returnConnectionToPool();
	}
	
	private static void fillMainPageForAdmin(HttpServletRequest request) {
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		UserDAO userDAO = factory.getUserDAO();
		try {
			List<User> userList = userDAO.getUsers();
			request.setAttribute("user_list", userList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		factory.returnConnectionToPool();
	}
	
	private static void fillMainPageForModerator(HttpServletRequest request) {
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		MessageAttachingDAO messageAttachDAO = factory.getMessageAttachingDAO();
		UserDAO userDAO = factory.getUserDAO();
//		TeamDAO teamDAO = factory.getTeamDAO();
//		String login = request.getParameter(PARAM_NAME_LOGIN);
		List<MessageAttaching> messageList = null;
		Set<MessageAttaching> deletedMessageSet = null;
		List<User> userList = null;
		try {
			userList = userDAO.getUnprivilegedUsers();
			messageList = messageAttachDAO.getAllMessageAttachs();
			deletedMessageSet = messageAttachDAO.getAllDeletedMessageAttachs();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("user_list", userList);
		request.setAttribute("messageList", messageList);
		request.setAttribute("deletedMessageSet", deletedMessageSet);
		factory.returnConnectionToPool();
	}
	
	private static void fillTeamSelectPageForUser(HttpServletRequest request) {
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		UserDAO userDAO = factory.getUserDAO();
		TeamDAO teamDAO = factory.getTeamDAO();
		String login = request.getParameter(PARAM_NAME_LOGIN);
		List<Team> teamList = null;
		try {
			User user = userDAO.getUserByLogin(login);
			teamList = teamDAO.getTeamsForUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		factory.returnConnectionToPool();
		request.setAttribute("teamList", teamList);
	}
	
	private static void fillTeamPageForUser(HttpServletRequest request) throws RedirectErrorPage {
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		MessageDAO messageDAO = factory.getMessageDAO();
		UserDAO userDAO = factory.getUserDAO();
		TeamDAO teamDAO = factory.getTeamDAO();
		String login = request.getParameter(PARAM_NAME_LOGIN);
		List<Message> messageList = null;
		Map<Message, Integer> deletedMessageSet = null;
		List<User> teamMembers = null;
		Team team = null;
		User teamExpert = null;
		try {
			User user = userDAO.getUserByLogin(login);
			List<Team> teamList = teamDAO.getTeamsForUser(user);
			team = teamDAO.getTeamById(Integer.parseInt(request.getParameter("team_id")));
			if (!teamList.contains(team)) {
				factory.returnConnectionToPool();
				throw new RedirectErrorPage();
			}
			teamExpert = userDAO.getExpertForTeam(team);
			teamMembers = userDAO.getTeamUserList(team);
			messageList = messageDAO.getAllMessagesForTeam(team);
			deletedMessageSet = messageDAO.getDeletedMessagesForTeam(team);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		factory.returnConnectionToPool();
		request.setAttribute("messageList", messageList);
		request.setAttribute("deletedMessageSet", deletedMessageSet);
		request.setAttribute("team", team);
		request.setAttribute("teamMembers", teamMembers);
		request.setAttribute("teamExpert", teamExpert);
	}

}
