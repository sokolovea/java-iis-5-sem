package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageAttachingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.MessageAttaching;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Setting;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
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
					DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
					SettingDAO settingDAO = factory.getSettingDAO();
					List<Setting> settingList = settingDAO.getSetting();
					request.setAttribute("setting_list", settingList);
					factory.returnConnectionToPool();
					return ConfigurationManager.getProperty("path.page.admin_settings");
				}
			} else if (loginResult == EnumLogin.USER) {
				if (activity.equals("send_message")) {
					DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
					MessageDAO messageDAO = factory.getMessageDAO();
					UserDAO userDAO = factory.getUserDAO();
					TeamDAO teamDAO = factory.getTeamDAO();
					MessageAttachingDAO messageAttachingDAO = factory.getMessageAttachingDAO();
					User user = userDAO.getUserByLogin(login);
					List<Team> teamList = teamDAO.getTeamsForUser(user);
					if (teamList.size() == 0) {
						System.out.println("Null team list for user!!!");
					}
					String messageString = request.getParameter("message");
					if (messageString == null) {
						System.out.println("Null message!!!"); //Message(id, data, author, time)
					}
					Message message = new Message();
					message.setAuthor(user);
					message.setData(messageString);
					MessageAttaching messageAttaching = new MessageAttaching();
					messageAttaching.setMessage(message);
					messageAttaching.setTeam(teamList.get(0));
					messageAttachingDAO.addMessage(messageAttaching);
					factory.returnConnectionToPool();
					return MenuCommand.getPage(loginResult, "team", request);
//					return ConfigurationManager.getProperty("path.page.team");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
