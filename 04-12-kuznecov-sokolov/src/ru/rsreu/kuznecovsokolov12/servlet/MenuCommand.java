package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import java.util.List;
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
				Set<Message> deletedMessageSet = null;
				try {
					User user = userDAO.getUserByLogin(login);
					List<Team> teamList = teamDAO.getTeamsForUser(user);
					messageList = messageDAO.getAllMessagesForTeam(teamList.get(0));
					deletedMessageSet = messageDAO.getDeletedMessagesForTeam(teamList.get(0));
					for (Message message: messageList) {
						System.out.println("deletedMessageSet.contains(message) = " + deletedMessageSet.contains(message));	
					}
				} catch (SQLException e) {
					;
				}
				request.setAttribute("messageList", messageList);
				request.setAttribute("deletedMessageSet", deletedMessageSet);
				return ConfigurationManager.getProperty("path.page.team");
			} else if (destination.equals("main")) {
				return ConfigurationManager.getProperty("path.page.team_select");
			}
		}
		if (loginResult == EnumLogin.MODERATOR) {
			if (destination.equals("main")) {
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
//				// Запись в базу данных новых настроек через DAO
//				щлщлщл
//				return ConfigurationManager.getProperty("path.page.admin_settings"); //возврат обратно
//			}
		}
		return ConfigurationManager.getProperty("path.page.login");
	}

}
