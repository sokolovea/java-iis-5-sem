package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.DeletedMessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageAttachingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.RoleDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamInteractDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.DeletedMessage;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.MessageAttaching;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Role;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Setting;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteract;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;
import test.RedirectErrorPage;

public class MenuCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			return ConfigurationManager.getProperty("path.page.index");
		}
		
		String login = (String) session.getAttribute(PARAM_USER_LOGIN);
		String password = (String) session.getAttribute(PARAM_USER_PASSWORD);
		String destination = request.getParameter(MenuCommand.PARAM_DESTINATION);
		EnumLogin loginResult = null;
		
		try {
			loginResult = LoginLogic.checkLogin(login, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return MenuCommand.getPage(destination, request);
	}
	
//	private static String getRequestAttribute(EnumLogin loginResult) {
//		if (loginResult != EnumLogin.NOUSER) {
//			return loginResult.toString();
//		}
//		return "errorLoginPassMessage";
//	}
	
	public static String getPage(String destination, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			return ConfigurationManager.getProperty("path.page.index");
		}
		
		EnumLogin loginResult = (EnumLogin) session.getAttribute(MenuCommand.PARAM_USER_ROLE);
		String login = (String) session.getAttribute(MenuCommand.PARAM_USER_LOGIN);
		if (loginResult == EnumLogin.USER) {
			if (destination.equals("exit_team")) {
				DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
				TeamDAO teamDAO = factory.getTeamDAO();
				TeamInteractDAO teamInteractDAO = factory.getTeamInteractDAO();
				UserDAO userDAO = factory.getUserDAO();
				User user;
				try {
					user = userDAO.getUserByLogin(login);
					List<Team> teamList = teamDAO.getTeamsForUser(user);
					if (teamList.size() != 0) {
						TeamInteract teamInteract = new TeamInteract(0, user,
								teamInteractDAO.getTeamInteractTypeByName("Exit"), teamList.get(0), null);
						teamInteractDAO.addTeamInteract(teamInteract);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				factory.returnConnectionToPool();
				return MenuCommand.getPage("main", request);
			}
		}
		if (loginResult == EnumLogin.USER || loginResult == EnumLogin.EXPERT || loginResult == EnumLogin.CAPTAIN) {
			if (destination.equals("team")) {
				try {
					int teamId = (int) request.getSession().getAttribute(MenuCommand.PARAM_TEAM_ID);
					if (loginResult == EnumLogin.EXPERT) {
						teamId = Integer.parseInt(request.getParameter("team_id"));
					}
					MenuCommand.fillTeamPageForUser(request, login, teamId);
				}
				catch (RedirectErrorPage e) {
					return ConfigurationManager.getProperty("path.page.error");
				}
				return ConfigurationManager.getProperty("path.page.team");
			} else if (destination.equals("main")) {
				MenuCommand.fillTeamSelectPageForUser(request, login);
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
		RoleDAO roleDAO = factory.getRoleDAO();
		try {
			List<User> userList = userDAO.getUsers();
			List<Role> roleList = roleDAO.getAllRoles();
			request.setAttribute("user_list", userList);
			request.setAttribute("roleList", roleList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		factory.returnConnectionToPool();
	}
	
	private static void fillMainPageForModerator(HttpServletRequest request) {
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		MessageAttachingDAO messageAttachDAO = factory.getMessageAttachingDAO();
		UserDAO userDAO = factory.getUserDAO();
		TeamDAO teamDAO = factory.getTeamDAO();
		List<MessageAttaching> messageList = null;
		Set<MessageAttaching> deletedMessageSet = null;
		List<User> userList = null;
		Map<Team, Map<String, Integer>> fullTeamMap = null;
		try {
			fullTeamMap = teamDAO.getAllTeam();
			userList = userDAO.getUnprivilegedUsers();
			messageList = messageAttachDAO.getAllMessageAttachs();
			deletedMessageSet = messageAttachDAO.getAllDeletedMessageAttachs();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("user_list", userList);
		request.setAttribute("messageList", messageList);
		request.setAttribute("deletedMessageSet", deletedMessageSet);
		request.setAttribute("fullTeamMap", fullTeamMap);
		factory.returnConnectionToPool();
	}
	
	private static void fillTeamSelectPageForUser(HttpServletRequest request, String login) {
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		UserDAO userDAO = factory.getUserDAO();
		TeamDAO teamDAO = factory.getTeamDAO();
		
		HttpSession session = request.getSession(false);
		List<Team> teamList = null;
		Map<Team, Map<String, Integer>> fullTeamMap = null;
		try {
			User user = userDAO.getUserByLogin(login);
			teamList = teamDAO.getTeamsForUser(user);
			if ((teamList != null) && (teamList.size() != 0)) {
				session.setAttribute(MenuCommand.PARAM_TEAM_ID, teamList.get(0).getId());
//				request.setAttribute("team_id", teamList.get(0).getId());
			}
//			else {
//				int teamId = Integer.getInteger(request.getParameter(MenuCommand.PARAM_TEAM_ID));
//				session.setAttribute(MenuCommand.PARAM_TEAM_ID, teamId);
//			}
			fullTeamMap = teamDAO.getAllTeam();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("fullTeamMap", fullTeamMap);
		factory.returnConnectionToPool();
		request.setAttribute("teamList", teamList);

	}
	
	private static void fillTeamPageForUser(HttpServletRequest request, String login, int teamId) throws RedirectErrorPage {
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		MessageDAO messageDAO = factory.getMessageDAO();
		UserDAO userDAO = factory.getUserDAO();
		TeamDAO teamDAO = factory.getTeamDAO();
		List<Message> messageList = null;
		Map<Message, Integer> deletedMessageSet = null;
		List<User> teamMembers = null;
		Team team = null;
		User teamExpert = null;
		try {
			User user = userDAO.getUserByLogin(login);
			List<Team> teamList = teamDAO.getTeamsForUser(user);
			
			team = teamDAO.getTeamById(teamId);
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
//		request.setAttribute("team_id", team.getId());
	}

}
