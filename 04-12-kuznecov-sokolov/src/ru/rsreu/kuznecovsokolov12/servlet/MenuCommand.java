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
import ru.rsreu.kuznecovsokolov12.datalayer.MessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.DeletedMessage;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Setting;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

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
			// TODO Auto-generated catch block
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
						return ConfigurationManager.getProperty("path.page.error");
					}
					teamExpert = userDAO.getExpertForTeam(team);
					teamMembers = userDAO.getTeamUserList(team);
					messageList = messageDAO.getAllMessagesForTeam(team);
					deletedMessageSet = messageDAO.getDeletedMessagesForTeam(team);
					for (Message message: messageList) {
						System.out.println("deletedMessageSet.contains(message) = " + deletedMessageSet.containsKey(message));	
					}
				} catch (SQLException e) {
					;
				}
				factory.returnConnectionToPool();
				request.setAttribute("messageList", messageList);
				request.setAttribute("deletedMessageSet", deletedMessageSet);
				request.setAttribute("team", team);
				request.setAttribute("teamMembers", teamMembers);
				request.setAttribute("teamExpert", teamExpert);
				return ConfigurationManager.getProperty("path.page.team");
			} else if (destination.equals("main")) {
				DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
				UserDAO userDAO = factory.getUserDAO();
				TeamDAO teamDAO = factory.getTeamDAO();
				String login = request.getParameter(PARAM_NAME_LOGIN);
				List<Team> teamList = null;
				try {
					User user = userDAO.getUserByLogin(login);
					teamList = teamDAO.getTeamsForUser(user);
				} catch (SQLException e) {
					;
				}
				factory.returnConnectionToPool();
				request.setAttribute("teamList", teamList);
				return ConfigurationManager.getProperty("path.page.team_select");
			}
		}
		if (loginResult == EnumLogin.MODERATOR) {
			DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
			if (destination.equals("main")) {
				UserDAO userDAO = factory.getUserDAO();
				try {
					List<User> userList = userDAO.getUnprivilegedUsers();
					request.setAttribute("user_list", userList);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					factory.returnConnectionToPool();
					e.printStackTrace();
				}
				factory.returnConnectionToPool();
				return ConfigurationManager.getProperty("path.page.moderator");
			}
		}
		if (loginResult == EnumLogin.ADMIN ) {
			DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
			if (destination.equals("settings")) {
				SettingDAO settingDAO = factory.getSettingDAO();
				try {
					List<Setting> settingList = settingDAO.getSetting();
					request.setAttribute("setting_list", settingList);
				} catch (SQLException e) {
					factory.returnConnectionToPool();
					e.printStackTrace();
				}
				factory.returnConnectionToPool();
				return ConfigurationManager.getProperty("path.page.admin_settings");
			} else if (destination.equals("main")) {
				UserDAO userDAO = factory.getUserDAO();
				try {
					List<User> userList = userDAO.getUsers();
					request.setAttribute("user_list", userList);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					factory.returnConnectionToPool();
					e.printStackTrace();
				}
				factory.returnConnectionToPool();
				return ConfigurationManager.getProperty("path.page.admin");
			}
//			} else if (destination.equals("settings_modify")) {
//				// ������ � ���� ������ ����� �������� ����� DAO
//				������
//				return ConfigurationManager.getProperty("path.page.admin_settings"); //������� �������
//			}
		}
		return ConfigurationManager.getProperty("path.page.login");
	}

}
