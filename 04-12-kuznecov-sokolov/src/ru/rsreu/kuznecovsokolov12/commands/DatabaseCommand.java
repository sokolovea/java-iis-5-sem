package ru.rsreu.kuznecovsokolov12.commands;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Role;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Setting;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;
import ru.rsreu.kuznecovsokolov12.exceptions.RedirectErrorPage;
import ru.rsreu.kuznecovsokolov12.servlet.EnumLogin;
import ru.rsreu.kuznecovsokolov12.utils.ConfigurationManager;

public class DatabaseCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {

		String activity = request.getParameter(DatabaseCommand.PARAM_ACTIVITY);

		String page = null;

		HttpSession session = request.getSession(false);
		if (session == null) {
			return DatabaseCommand.URL_LOGIN_PAGE;
		}
		
		DatabaseLogic.initDAOitems();

		String login = (String) session.getAttribute(DatabaseCommand.PARAM_USER_LOGIN);
		String password = (String) session.getAttribute(DatabaseCommand.PARAM_USER_PASSWORD);
		
		EnumLogin loginResult = (EnumLogin) session.getAttribute(MenuCommand.PARAM_USER_ROLE);
		
		try {

			if (LoginLogic.checkLogin(login, password) == EnumLogin.NOUSER) {
				return DatabaseCommand.URL_LOGIN_PAGE;
			}
			
			if (loginResult == EnumLogin.ADMIN) {
				page = DatabaseCommand.getPageForAdmin(request, activity);
			}

			else if (loginResult == EnumLogin.USER) {
				page = DatabaseCommand.getPageForUser(request, activity);
			}

			else if (loginResult == EnumLogin.EXPERT) {
				page = DatabaseCommand.getPageForExpert(request, activity);
			}

			else if (loginResult == EnumLogin.MODERATOR) {
				page = DatabaseCommand.getPageForModerator(request, activity);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DatabaseLogic.closeFactory();

		if (page != null) {
			return page;
		}
		return DatabaseCommand.URL_LOGIN_PAGE;
	}

	private static String getPageForUser(HttpServletRequest request, String activity) throws SQLException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			return DatabaseCommand.URL_LOGIN_PAGE;
		}
		String login = (String) session.getAttribute(DatabaseCommand.PARAM_USER_LOGIN);

		if (activity.equals(ActionCommand.ACTIVITY_SEND_MESSAGE)) {
			String message = request.getParameter(DatabaseCommand.PARAM_MESSAGE);
			Integer teamId = (Integer) session.getAttribute(DatabaseCommand.PARAM_TEAM_ID);
			if (message != null && !message.isEmpty()) {
				DatabaseLogic.sendMessage(login, message, teamId);
			}
			return DatabaseCommand.URL_TEAM_PAGE;
		}

		else if (activity.equals(ActionCommand.ACTIVITY_DELETE_MESSAGE)) {
			int messageId = Integer.parseInt(request.getParameter(DatabaseCommand.PARAM_MESSAGE_ID));
			DatabaseLogic.deleteMessage(login, messageId);
			return DatabaseCommand.URL_TEAM_PAGE;
		}

		else if (activity.equals(ActionCommand.ACTIVITY_RESTORE_MESSAGE)) {
			int messageId = Integer.parseInt(request.getParameter(DatabaseCommand.PARAM_MESSAGE_ID));
			DatabaseLogic.restoreMessage(messageId);
			return DatabaseCommand.URL_TEAM_PAGE;
		}

		else if (activity.equals(ActionCommand.ACTIVITY_CREATE_TEAM)) {
			String teamFormName = request.getParameter("teamFormName");
			try {
				DatabaseLogic.createTeam(login, teamFormName);
			} catch (RedirectErrorPage e) {
				session.setAttribute("errorTeamCreate", e.getMessage());
			}
			return DatabaseCommand.URL_MAIN_PAGE;
		}

		else if (activity.equals(ActionCommand.ACTIVITY_JOIN_TEAM)) {
			String teamId = request.getParameter(DatabaseCommand.PARAM_TEAM_ID);
			DatabaseLogic.userJoinTeam(login, Integer.parseInt(teamId));
			return DatabaseCommand.URL_MAIN_PAGE;
		}

		else if (activity.equals(ActionCommand.ACTIVITY_POP_EXPERT)) {
			int teamId = (int) session.getAttribute(DatabaseCommand.PARAM_TEAM_ID);
			DatabaseLogic.ejectExpertFromTeam(login, teamId);
			return DatabaseCommand.URL_TEAM_PAGE;
		}
		return null;
	}

	private static String getPageForExpert(HttpServletRequest request, String activity) throws SQLException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			return ConfigurationManager.getProperty("path.page.index");
		}
		String login = (String) session.getAttribute(DatabaseCommand.PARAM_USER_LOGIN);
		String team_id = request.getParameter(DatabaseCommand.PARAM_TEAM_ID);

		if (activity.equals(ActionCommand.ACTIVITY_JOIN_TEAM)) {
			int teamId = Integer.parseInt(team_id);
			DatabaseLogic.expertJoinTeam(login, teamId);
			return DatabaseCommand.URL_MAIN_PAGE;
		}

		else if (activity.equals(ActionCommand.ACTIVITY_EXPERT_EXIT_TEAM)) {
			int teamId = Integer.parseInt(team_id);
			DatabaseLogic.expertExitFromTeam(login, teamId);
			return DatabaseCommand.URL_MAIN_PAGE;
		}

		else if (activity.equals(ActionCommand.ACTIVITY_SEND_MESSAGE)) {
			String message = request.getParameter(DatabaseCommand.PARAM_MESSAGE);
			if (message != null && !message.isEmpty()) {
				DatabaseLogic.sendMessage(login, message, Integer.parseInt(team_id));
			}
			return String.format(DatabaseCommand.URL_TEAM_PAGE_AND_TEAM_ID, team_id);
		}

		else if (activity.equals(ActionCommand.ACTIVITY_DELETE_MESSAGE)) {
			int messageId = Integer.parseInt(request.getParameter(DatabaseCommand.PARAM_MESSAGE_ID));
			DatabaseLogic.deleteMessage(login, messageId);
			return String.format(DatabaseCommand.URL_TEAM_PAGE_AND_TEAM_ID, team_id);
		}

		else if (activity.equals(ActionCommand.ACTIVITY_RESTORE_MESSAGE)) {
			int messageId = Integer.parseInt(request.getParameter(DatabaseCommand.PARAM_MESSAGE_ID));
			DatabaseLogic.restoreMessage(messageId);
			return String.format(DatabaseCommand.URL_TEAM_PAGE_AND_TEAM_ID, team_id);
		}
		return null;
	}

	private static String getPageForModerator(HttpServletRequest request, String activity) throws SQLException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			return ConfigurationManager.getProperty("path.page.index");
		}
		String login = (String) session.getAttribute(DatabaseCommand.PARAM_USER_LOGIN);

		if (activity.equals(ActionCommand.ACTIVITY_DELETE_MESSAGE)) {
			int messageId = Integer.parseInt(request.getParameter(DatabaseCommand.PARAM_MESSAGE_ID));
			DatabaseLogic.deleteMessage(login, messageId);
			return DatabaseCommand.URL_MAIN_PAGE;

		}

		else if (activity.equals(ActionCommand.ACTIVITY_RESTORE_MESSAGE)) {
			int messageId = Integer.parseInt(request.getParameter(DatabaseCommand.PARAM_MESSAGE_ID));
			DatabaseLogic.restoreMessage(messageId);
			return DatabaseCommand.URL_MAIN_PAGE;
		}

		else if (activity.equals(ActionCommand.ACTIVITY_ADD_SANCTION)) {
			int userIdForSanction = Integer.parseInt(request.getParameter(ActionCommand.PARAM_USER_ID));
			String sanction = request.getParameter(ActionCommand.PARAM_SANCTION);
			String reason = request.getParameter(ActionCommand.PARAM_REASON);
			DatabaseLogic.createSanction(login, userIdForSanction, sanction, reason);
			return DatabaseCommand.URL_MAIN_PAGE;
		}
		return null;
	}

	private static String getPageForAdmin(HttpServletRequest request, String activity) throws SQLException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			return ConfigurationManager.getProperty("path.page.index");
		}
		String login = (String) session.getAttribute(DatabaseCommand.PARAM_USER_LOGIN);

		if (activity.equals(ActionCommand.ACTIVITY_UPDATE_SETTING)) {
			String teamCapacity = request.getParameter("teamCapacity");
			String expertCapacity = request.getParameter("expertCapacity");
			List<Setting> settingList = DatabaseLogic.updateSetting(teamCapacity, expertCapacity);
			request.setAttribute("setting_list", settingList);
			return DatabaseCommand.URL_SETTINGS_PAGE;
		}

		else if (activity.equals(ActionCommand.ACTIVITY_UPDATE_USER)) {
			String commandType = request.getParameter("command_type");
			String userLogin = request.getParameter("form_login");
			String userPassword = request.getParameter("form_password");
			String userName = request.getParameter("form_name");
			String userEmail = request.getParameter("form_email");
			String userRole = request.getParameter("form_role");
			if (commandType.equals(ActionCommand.ACTIVITY_SAVE_USER)) {
				DatabaseLogic.saveUser(new User(-1, userLogin, userPassword, userName, userEmail), userRole, login);
			}

			else if (commandType.equals(ActionCommand.ACTIVITY_CREATE_USER)) {
				DatabaseLogic.createUser(new User(userLogin, userPassword, userName, userEmail), userRole, login);
			}

			else if (commandType.equals(ActionCommand.ACTIVITY_REMOVE_USER)) {
				String currentAdminLogin = (String) session.getAttribute(DatabaseCommand.PARAM_USER_LOGIN);
				if (!currentAdminLogin.equals(userLogin)) {
					DatabaseLogic.removeUser(userLogin);
				}
				return MenuCommand.getPage(ActionCommand.DESTINATION_MAIN, request);
			}

			else if (commandType.equals(ActionCommand.ACTIVITY_FIND_USER)) {
				Map.Entry<User, Role> user = DatabaseLogic.findUser(userLogin);
				if (user != null) {
					request.setAttribute("form_login", user.getKey().getLogin());
					request.setAttribute("form_password", user.getKey().getPassword());
					request.setAttribute("form_name", user.getKey().getName());
					request.setAttribute("form_email", user.getKey().getEmail());
					request.setAttribute("form_role", user.getValue().getName());
				}
				return MenuCommand.getPage(ActionCommand.DESTINATION_MAIN, request);
			}
		}
		return null;
	}

}
