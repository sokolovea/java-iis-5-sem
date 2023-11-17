package ru.rsreu.kuznecovsokolov12.servlet;

import java.net.http.HttpRequest;
import java.sql.SQLException;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.DeletedMessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageAttachingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.RoleAssigmentDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.RoleDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.SanctionDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamInteractDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.DeletedMessage;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.MessageAttaching;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Role;
import ru.rsreu.kuznecovsokolov12.datalayer.data.RoleAssigment;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Sanction;
import ru.rsreu.kuznecovsokolov12.datalayer.data.SanctionType;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Setting;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteract;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteractType;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;
import ru.rsreu.kuznecovsokolov12.datalayer.oracledb.OracleSanctionDAO;

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
		
		String page = null;
		DatabaseLogic.initDAOitems();
		
		try {
			loginResult = LoginLogic.checkLogin(login, password);
			
			if (loginResult == EnumLogin.ADMIN) {
				page = DatabaseCommand.getPageForAdmin(request, activity, loginResult, login);
			} 
			
			else if (loginResult == EnumLogin.USER) {
				page = DatabaseCommand.getPageForUser(request, activity, loginResult, login);
			}
			
			else if (loginResult == EnumLogin.EXPERT) {
				page = DatabaseCommand.getPageForExpert(request, activity, loginResult, login);
			}
			
			else if (loginResult == EnumLogin.MODERATOR) {
				page = DatabaseCommand.getPageForModerator(request, activity, loginResult, login);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute(DatabaseCommand.getRequestAttribute(loginResult), login);
		
		DatabaseLogic.closeFactory();
		
		if (page != null) {
			return page;
		}
		return ConfigurationManager.getProperty("path.page.login");
	}

	private static String getRequestAttribute(EnumLogin loginResult) {
		if (loginResult != EnumLogin.NOUSER) {
			return loginResult.toString();
		}
		return "errorLoginPassMessage";
	}

	
	private static String getPageForUser(HttpServletRequest request, String activity, EnumLogin loginResult, String login) throws SQLException {
		if (activity.equals("send_message")) {
			String message = request.getParameter("message");
			if (message != null && !message.isEmpty()) {
				DatabaseLogic.sendMessage(login, message);
			}
			return MenuCommand.getPage(loginResult, "team", request);
		} 
		
		else if (activity.equals("delete_message")) {
			int messageId = Integer.parseInt(request.getParameter("messageId"));
			DatabaseLogic.deleteMessage(login, messageId);
			return MenuCommand.getPage(loginResult, "team", request);
		} 
		
		else if (activity.equals("restore_message")) {
			int messageId = Integer.parseInt(request.getParameter("messageId"));
			DatabaseLogic.restoreMessage(messageId);
			return MenuCommand.getPage(loginResult, "team", request);
		} 
		
		else if (activity.equals("create_team")) {
			String teamFormName = request.getParameter("teamFormName");
			DatabaseLogic.createTeam(login, teamFormName);
			return MenuCommand.getPage(loginResult, "main", request);
		} 
		
		else if (activity.equals("join_team")) {
			int teamId = Integer.parseInt(request.getParameter("team_id"));
			DatabaseLogic.userJoinTeam(login, teamId);
			return MenuCommand.getPage(loginResult, "main", request);
		} 
		
		else if (activity.equals("captain_pop_expert")) {
			int teamId = Integer.parseInt(request.getParameter("team_id"));
			DatabaseLogic.ejectExpertFromTeam(login, teamId);
			return MenuCommand.getPage(loginResult, "team", request);
		}
		return null;
	}
	
	private static String getPageForExpert(HttpServletRequest request, String activity, EnumLogin loginResult, String login) throws SQLException {
		if (activity.equals("join_team")) {
			int teamId = Integer.parseInt(request.getParameter("team_id"));
			DatabaseLogic.expertJoinTeam(login, teamId);
			return MenuCommand.getPage(loginResult, "main", request);
		} 
		
		else if (activity.equals("expert_exit_team")) {
			int teamId = Integer.parseInt(request.getParameter("team_id"));
			DatabaseLogic.expertExitFromTeam(login, teamId);
			return MenuCommand.getPage(loginResult, "main", request);
		} 	
		
		else if (activity.equals("send_message")) {
			String message = request.getParameter("message");
			if (message != null && !message.isEmpty()) {
				DatabaseLogic.sendMessage(login, message);
			}
			return MenuCommand.getPage(loginResult, "team", request);
		} 
		
		else if (activity.equals("delete_message")) {
			int messageId = Integer.parseInt(request.getParameter("messageId"));
			DatabaseLogic.deleteMessage(login, messageId);
			return MenuCommand.getPage(loginResult, "team", request);
		} 
		
		else if (activity.equals("restore_message")) {
			int messageId = Integer.parseInt(request.getParameter("messageId"));
			DatabaseLogic.restoreMessage(messageId);
			return MenuCommand.getPage(loginResult, "team", request);
		}
		return null;
	}
	
	private static String getPageForModerator(HttpServletRequest request, String activity, EnumLogin loginResult, String login) throws SQLException {
		if (activity.equals("delete_message")) {
			int messageId = Integer.parseInt(request.getParameter("messageId"));
			DatabaseLogic.deleteMessage(login, messageId);
			return MenuCommand.getPage(loginResult, "main", request);

		} 
		
		else if (activity.equals("restore_message")) {
			int messageId = Integer.parseInt(request.getParameter("messageId"));
			DatabaseLogic.restoreMessage(messageId);
			return MenuCommand.getPage(loginResult, "main", request);
		} 
		
		else if (activity.equals("add_sanction")) {
			int userIdForSanction = Integer.parseInt(request.getParameter("user_id"));
			String sanction = request.getParameter("sanction");
			DatabaseLogic.createSanction(login, userIdForSanction, sanction);
			return MenuCommand.getPage(loginResult, "main", request);
		}
		return null;
	}
	
	private static String getPageForAdmin(HttpServletRequest request, String activity, EnumLogin loginResult, String login) throws SQLException {
		if (activity.equals("update_setting")) {
			String teamCapacity = request.getParameter("teamCapacity");
			String expertCapacity = request.getParameter("expertCapacity");
			List<Setting> settingList = DatabaseLogic.updateSetting(teamCapacity, expertCapacity);
			request.setAttribute("setting_list", settingList);
			return ConfigurationManager.getProperty("path.page.admin_settings");
		} 
		
		else if (activity.equals("update_user")) {
			String commandType = request.getParameter("command_type");
			String userLogin = request.getParameter("form_login");
			String userPassword = request.getParameter("form_password");
			String userName = request.getParameter("form_name");
			String userEmail = request.getParameter("form_email");
			String userRole = request.getParameter("form_role");
			if (commandType.equals("save_user")) {
				DatabaseLogic.saveUser(new User(-1, userLogin, userPassword, userName, userEmail));
			} 
			
			else if (commandType.equals("create_user")) {
				DatabaseLogic.createUser(new User(userLogin, userPassword, userName, userEmail), userRole, login);
			} 
			
			else if (commandType.equals("remove_user")) {
				DatabaseLogic.removeUser(userLogin);
				return MenuCommand.getPage(loginResult, "main", request);
			} 
			
			else if (commandType.equals("find_user")) {
				Map.Entry<User, Role> user = DatabaseLogic.findUser(userLogin);
				if (user != null) {
					request.setAttribute("form_login", user.getKey().getLogin());
					request.setAttribute("form_password", user.getKey().getPassword());
					request.setAttribute("form_name", user.getKey().getName());
					request.setAttribute("form_email", user.getKey().getEmail());
					request.setAttribute("form_role", user.getValue().getName());
				}
				return MenuCommand.getPage(loginResult, "main", request);
			}
		}
		return null;
	}
	
	
}
