package ru.rsreu.kuznecovsokolov12.servlet;

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
import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamInteractDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.DeletedMessage;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.MessageAttaching;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Role;
import ru.rsreu.kuznecovsokolov12.datalayer.data.RoleAssigment;
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
					String commandType = request.getParameter("command_type"); // Update user, Modify user, delete user
																				// and etc.
					DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
					UserDAO userDAO = factory.getUserDAO();
					RoleDAO roleDAO = factory.getRoleDAO();
					RoleAssigmentDAO roleAssigmentDAO = factory.getRoleAssigmentDAO();
					String userLogin = request.getParameter("form_login");
					String userPassword = request.getParameter("form_password");
					String userName = request.getParameter("form_name");
					String userEmail = request.getParameter("form_email");
					String userRole = request.getParameter("form_role");
					if (commandType.equals("save_user")) {
						User tempUser = userDAO.getUserByLogin(userLogin);
						if (tempUser.getLogin() != null && (!userPassword.isEmpty())) {
							tempUser.setPassword(userPassword);
							tempUser.setEmail(userEmail);
							tempUser.setName(userName);
							// !!! Role do not changed!
							userDAO.updateUser(tempUser);
							factory.returnConnectionToPool();
						}
						factory.returnConnectionToPool();
					} else if (commandType.equals("create_user")) {
						User tempUser = userDAO.getUserByLogin(userLogin);
						if (tempUser.getLogin() == null && (!userPassword.isEmpty())) {
							tempUser.setLogin(userLogin);
							tempUser.setPassword(userPassword);
							tempUser.setEmail(userEmail);
							tempUser.setName(userName);
							Role role = roleDAO.getRoleByName(userRole);
							User sender = userDAO.getUserByLogin(login);
							userDAO.addUser(tempUser);
							tempUser = userDAO.getUserByLogin(tempUser.getLogin());
							RoleAssigment roleAssigment = new RoleAssigment(0, role, sender, tempUser, null);
							roleAssigmentDAO.addRoleAssigment(roleAssigment);
							factory.returnConnectionToPool();
						}
						factory.returnConnectionToPool();
					} else if (commandType.equals("remove_user")) {
						User tempUser = userDAO.getUserByLogin(userLogin);
						if (tempUser.getLogin() != null) {
							userDAO.deleteUser(tempUser);
						}
						System.out.println("Command Type " + commandType);
						factory.returnConnectionToPool();
						return MenuCommand.getPage(loginResult, "main", request);
					} else if (commandType.equals("find_user")) {
						User tempUser = userDAO.getUserByLogin(userLogin);
						if (tempUser.getLogin() != null) {
							String tempLogin = tempUser.getLogin();
							String tempPassword = tempUser.getPassword();
							String tempName = tempUser.getName();
							String tempEmail = tempUser.getEmail();
							Role role = roleDAO.getUserRole(tempUser);
							String tempRole = role.getName();
							request.setAttribute("form_login", tempLogin);
							request.setAttribute("form_password", tempPassword);
							request.setAttribute("form_name", tempName);
							request.setAttribute("form_email", tempEmail);
							request.setAttribute("form_role", tempRole);
						}
						System.out.println("Command Type " + commandType);
						factory.returnConnectionToPool();
						return MenuCommand.getPage(loginResult, "main", request);
					}
				}
			} else if (loginResult == EnumLogin.USER) {
				if (activity.equals("send_message")) {
					String message = request.getParameter("message");
					if (message != null && !message.isEmpty()) {
						DatabaseLogic.sendMessage(login, message);
					}
					return MenuCommand.getPage(loginResult, "team", request);
//					return ConfigurationManager.getProperty("path.page.team");
				
				
				} else if (activity.equals("delete_message")) {
					int messageId = Integer.parseInt(request.getParameter("messageId"));
					DatabaseCommand.deleteMessage(login, messageId);
					return MenuCommand.getPage(loginResult, "team", request);
//					return ConfigurationManager.getProperty("path.page.team");
				
				
				} else if (activity.equals("restore_message")) {
					int messageId = Integer.parseInt(request.getParameter("messageId"));
					DatabaseCommand.restoreMessage(messageId);
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
						factory.returnConnectionToPool();
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
						DeletedMessage deletedMessage = new DeletedMessage(0, user,
								new Message(Integer.parseInt(message)), null);
						deletedMessageDAO.addDeletedMessage(deletedMessage);
						factory.returnConnectionToPool();
						return MenuCommand.getPage(loginResult, "team", request);
//					return ConfigurationManager.getProperty("path.page.team");
					} else if (activity.equals("restore_message")) {
						DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
						DeletedMessageDAO deletedMessageDAO = factory.getDeletedMessageDAO();
						String message = request.getParameter("messageId");
						DeletedMessage deletedMessage = new DeletedMessage(0, null,
								new Message(Integer.parseInt(message)), null);
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
						for (Team tempTeam : allTeams.keySet()) {
							if (tempTeam.getName().equals(teamFormName)) {
								teamExists = true;
								break;
							}
						}
						if (teamFormName != null && !teamFormName.isEmpty() && (!teamExists)) {
							User user = userDAO.getUserByLogin(login);
							List<Team> teamList = teamDAO.getTeamsForUser(user);
							if (teamList.size() != 0) {
								TeamInteract teamInteract = new TeamInteract(0, user,
										teamInteractDAO.getTeamInteractTypeByName("Exit"), teamList.get(0), null);
								teamInteractDAO.addTeamInteract(teamInteract);
							}
							Team team = new Team();
							team.setName(teamFormName);
							teamDAO.addTeam(team);
							team = teamDAO.getTeamByName(teamFormName);
							TeamInteract teamInteract = new TeamInteract(0, user,
									teamInteractDAO.getTeamInteractTypeByName("Join"), team, null);
							teamInteractDAO.addTeamInteract(teamInteract);
						}
						factory.returnConnectionToPool();
						return MenuCommand.getPage(loginResult, "main", request);
//					return ConfigurationManager.getProperty("path.page.team");
					} else if (activity.equals("join_team")) {
						int teamId = Integer.parseInt(request.getParameter("team_id"));
						DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
						TeamDAO teamDAO = factory.getTeamDAO();
						TeamInteractDAO teamInteractDAO = factory.getTeamInteractDAO();
						UserDAO userDAO = factory.getUserDAO();
						User user = userDAO.getUserByLogin(login);
						List<Team> teamList = teamDAO.getTeamsForUser(user);
						if (teamList.size() != 0) {
							if (LoginLogic.isCapitan(login, teamList.get(0).getId()) || (teamList.get(0).getId() == teamId)) {
								factory.returnConnectionToPool();
								return MenuCommand.getPage(loginResult, "main", request);
							}
							TeamInteract teamInteract = new TeamInteract(0, user, teamInteractDAO.getTeamInteractTypeByName("Exit"), teamList.get(0), null);
							teamInteractDAO.addTeamInteract(teamInteract);
						}
						Team team = teamDAO.getTeamById(teamId);
						TeamInteract teamInteract = new TeamInteract(0, user, teamInteractDAO.getTeamInteractTypeByName("Join"), team, null);
						teamInteractDAO.addTeamInteract(teamInteract);
						factory.returnConnectionToPool();
						return MenuCommand.getPage(loginResult, "main", request);
					}
			else if (loginResult == EnumLogin.MODERATOR) {
				if (activity.equals("delete_message")) {
					int messageId = Integer.parseInt(request.getParameter("messageId"));
					DatabaseCommand.deleteMessage(login, messageId);
					return MenuCommand.getPage(loginResult, "main", request);
				
				
				} else if (activity.equals("restore_message")) {
					int messageId = Integer.parseInt(request.getParameter("messageId"));
					DatabaseCommand.restoreMessage(messageId);
					return MenuCommand.getPage(loginResult, "main", request);
				
				
				}
			}
 
			}
			catch (SQLException e) {
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
	
	private static void deleteMessage(String senderLogin, int messageId) throws SQLException {
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		UserDAO userDAO = factory.getUserDAO();
		DeletedMessageDAO deletedMessageDAO = factory.getDeletedMessageDAO();
		User user = userDAO.getUserByLogin(senderLogin);
		DeletedMessage deletedMessage = new DeletedMessage(0, user, new Message(messageId), null);
		deletedMessageDAO.addDeletedMessage(deletedMessage);
		factory.returnConnectionToPool();
	}
	
	private static void restoreMessage(int messageId) throws SQLException {
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		DeletedMessageDAO deletedMessageDAO = factory.getDeletedMessageDAO();
		DeletedMessage deletedMessage = new DeletedMessage(0, null, new Message(messageId), null);
		deletedMessageDAO.removeFromDeletedMessage(deletedMessage);
		factory.returnConnectionToPool();
	}
}
