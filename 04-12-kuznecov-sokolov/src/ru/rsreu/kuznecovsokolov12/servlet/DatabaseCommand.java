package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.DeletedMessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageAttachingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamInteractDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.DeletedMessage;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.MessageAttaching;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Setting;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteract;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteractType;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class DatabaseCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";

	@Override
	public String execute(HttpServletRequest request) {
		String login = request.getParameter(PARAM_NAME_LOGIN);
		request.getSession().setAttribute("userName", login);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		request.getSession().setAttribute("userPassword", password);
		String activity = request.getParameter("activity");
		EnumLogin loginResult = null;
		try {
			loginResult = LoginLogic.checkLogin(login, password);
			if (loginResult == EnumLogin.ADMIN) {
				if (activity.equals("update_setting")) {
					String teamCapacity = request.getParameter("teamCapacity");
					String expertCapacity = request.getParameter("expertCapacity");
					System.out.println(login + "; " + password + "; " + teamCapacity + "; " + expertCapacity
							+ "; activity=" + activity);
					DatabaseLogic.updateSetting(teamCapacity, expertCapacity);
					// TODO
					DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
					SettingDAO settingDAO = factory.getSettingDAO();
					List<Setting> settingList = settingDAO.getSetting();
					request.setAttribute("setting_list", settingList);
					factory.returnConnectionToPool();
					// TODO - end
					return ConfigurationManager.getProperty("path.page.admin_settings");
				} else if (activity.equals("update_user")) {
					String commandType = request.getParameter("command_type"); //Update user, Modify user, delete user and etc.
					System.out.println("Command Type " + commandType);
					DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
					UserDAO userDAO = factory.getUserDAO();
					factory.returnConnectionToPool();
					return MenuCommand.getPage(loginResult, "main", request);
				}
			} else if (loginResult == EnumLogin.USER) {
				if (activity.equals("send_message")) {
					String message = request.getParameter("message");
					DatabaseLogic.sendMessage(login, message);
					return MenuCommand.getPage(loginResult, "team", request);
//					return ConfigurationManager.getProperty("path.page.team");
				} else if (activity.equals("delete_message")) {
					DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
					UserDAO userDAO = factory.getUserDAO();
					DeletedMessageDAO deletedMessageDAO = factory.getDeletedMessageDAO();
					User user = userDAO.getUserByLogin(login);
					String message = request.getParameter("messageId");
					DeletedMessage deletedMessage = new DeletedMessage(0, user, new Message(Integer.parseInt(message)), null);
					deletedMessageDAO.addDeletedMessage(deletedMessage);
					factory.returnConnectionToPool();
					return MenuCommand.getPage(loginResult, "team", request);
//					return ConfigurationManager.getProperty("path.page.team");
				} else if (activity.equals("restore_message")) {
					DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
					DeletedMessageDAO deletedMessageDAO = factory.getDeletedMessageDAO();
					String message = request.getParameter("messageId");
					DeletedMessage deletedMessage = new DeletedMessage(0, null, new Message(Integer.parseInt(message)), null);
					deletedMessageDAO.removeFromDeletedMessage(deletedMessage);
					factory.returnConnectionToPool();
					return MenuCommand.getPage(loginResult, "team", request);
//					return ConfigurationManager.getProperty("path.page.team");
				} else if (activity.equals("create_team")) {
					DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
					TeamDAO teamDAO = factory.getTeamDAO();
					TeamInteractDAO teamInteractDAO = factory.getTeamInteractDAO();
					UserDAO userDAO = factory.getUserDAO();
					String teamFormName = request.getParameter("teamFormName");
					Map<Team, Map<String, Integer>> allTeams = teamDAO.getAllTeam();
					boolean teamExists = false;
					for (Team tempTeam: allTeams.keySet()) {
						if (tempTeam.getName().equals(teamFormName)) {
							teamExists = true;
							break;
						}
					}
					if (teamFormName != null && !teamFormName.isEmpty() && (!teamExists)) {
						Team team = new Team();
						team.setName(teamFormName);
						teamDAO.addTeam(team);
						User user = userDAO.getUserByLogin(login);
						team = teamDAO.getTeamByName(teamFormName);
						TeamInteract teamInteract = new TeamInteract(0, user, teamInteractDAO.getTeamInteractTypeByName("Join"), team, null);
						teamInteractDAO.addTeamInteract(teamInteract);
					}
					factory.returnConnectionToPool();
					return MenuCommand.getPage(loginResult, "main", request);
//					return ConfigurationManager.getProperty("path.page.team");
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute(DatabaseCommand.getRequestAttribute(loginResult), login);
		System.out.println("DatabaseCommand.getPage(loginResult); = " + DatabaseCommand.getPage(loginResult));

		return DatabaseCommand.getPage(loginResult);
	}

	private static String getRequestAttribute(EnumLogin loginResult) {
		if (loginResult != EnumLogin.NOUSER) {
			return loginResult.toString();
		}
		return "errorLoginPassMessage";
	}

	private static String getPage(EnumLogin loginResult) {
		if (loginResult != EnumLogin.NOUSER) {
			return ConfigurationManager.getProperty("path.page.reports");
		}
		return ConfigurationManager.getProperty("path.page.login");
	}
}
