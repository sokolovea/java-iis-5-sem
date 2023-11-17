package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jdk.jshell.UnresolvedReferenceException;
import ru.rsreu.kuznecovsokolov12.datalayer.data.*;
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
import ru.rsreu.kuznecovsokolov12.datalayer.oracledb.DAOAcces;
import ru.rsreu.kuznecovsokolov12.datalayer.oracledb.OracleDataBaseDAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.oracledb.OracleUserDAO;

public class DatabaseLogic implements DAOAcces {
	/*
	public static final DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
	public static final SettingDAO settingDAO = factory.getSettingDAO();
	public static final UserDAO userDAO = factory.getUserDAO();
	public static final RoleDAO roleDAO = factory.getRoleDAO();
	public static final DeletedMessageDAO deletedMessageDAO = factory.getDeletedMessageDAO();
	public static final SanctionDAO sanctionDAO = factory.getSanctionDAO();
	public static final MessageAttachingDAO messageAttachDAO = factory.getMessageAttachingDAO();
	public static final MessageDAO messageDAO = factory.getMessageDAO();
	public static final RoleAssigmentDAO roleAssigmentDAO = factory.getRoleAssigmentDAO();
	public static final TeamDAO teamDAO = factory.getTeamDAO();
	public static final TeamInteractDAO teamInteractDAO = factory.getTeamInteractDAO();
	*/
	

	public static List<Setting> updateSetting(String teamCapacity, String expertCapacity) throws SQLException {
		Setting settingTeamCapacity = new Setting("max_team_capacity", Integer.parseInt(teamCapacity));
		Setting settingExpertCapacity = new Setting("max_team_consulted_expert", Integer.parseInt(expertCapacity));
		settingDAO.setSetting(settingTeamCapacity);
		settingDAO.setSetting(settingExpertCapacity);
		return settingDAO.getSetting();
	}
	
	public static void sendMessage(String login, String messageData) throws SQLException {
		User user = userDAO.getUserByLogin(login);
		List<Team> teamList = teamDAO.getTeamsForUser(user);
		if (teamList.size() == 0) {
			System.out.println("Null team list for user!!!");
		}
		if (messageData == null) {
			System.out.println("Null message!!!"); //Message(id, data, author, time)
		}
		Message message = new Message();
		message.setAuthor(user);
		message.setData(messageData);
		MessageAttaching messageAttaching = new MessageAttaching();
		messageAttaching.setMessage(message);
		messageAttaching.setTeam(teamList.get(0));
		messageAttachDAO.addMessage(messageAttaching);
		factory.returnConnectionToPool();

	}
	
	
	public static void createTeam(String userLogin, String teamName) throws SQLException {
		Map<Team, Map<String, Integer>> allTeams = teamDAO.getAllTeam();
		boolean teamExists = false;
		for (Team tempTeam : allTeams.keySet()) {
			if (tempTeam.getName().equals(teamName)) {
				teamExists = true;
				break;
			}
		}
		if (teamName != null && !teamName.isEmpty() && (!teamExists)) {
			User user = userDAO.getUserByLogin(userLogin);
			List<Team> teamList = teamDAO.getTeamsForUser(user);
			if (teamList.size() != 0) {
				if (LoginLogic.isCapitan(userLogin, teamList.get(0).getId())) {
					factory.returnConnectionToPool();
					return;
					//return MenuCommand.getPage(loginResult, "main", request);
				}
				TeamInteract teamInteract = new TeamInteract(0, user, teamInteractDAO.getTeamInteractTypeByName("Exit"), teamList.get(0), null);
				teamInteractDAO.addTeamInteract(teamInteract);
			}
			Team team = new Team();
			team.setName(userLogin);
			teamDAO.addTeam(team);
			team = teamDAO.getTeamByName(userLogin);
			TeamInteract teamInteract = new TeamInteract(0, user, teamInteractDAO.getTeamInteractTypeByName("Join"), team, null);
			teamInteractDAO.addTeamInteract(teamInteract);
		}
		factory.returnConnectionToPool();

	}
	
	public static void userJoinTeam(String userLogin, int teamId) throws SQLException {
		User user = userDAO.getUserByLogin(userLogin);
		List<Team> teamList = teamDAO.getTeamsForUser(user);
		if (teamList.size() != 0) {
			if (LoginLogic.isCapitan(userLogin, teamList.get(0).getId()) || (teamList.get(0).getId() == teamId)) {
				factory.returnConnectionToPool();
				return;
			}
			TeamInteract teamInteract = new TeamInteract(0, user,
					teamInteractDAO.getTeamInteractTypeByName("Exit"), teamList.get(0), null);
			teamInteractDAO.addTeamInteract(teamInteract);
		}
		Team team = teamDAO.getTeamById(teamId);
		TeamInteract teamInteract = new TeamInteract(0, user,
				teamInteractDAO.getTeamInteractTypeByName("Join"), team, null);
		teamInteractDAO.addTeamInteract(teamInteract);
		factory.returnConnectionToPool();
	}
	
	public static void expertJoinTeam(String expertLogin, int teamId) throws SQLException {
		User user = userDAO.getUserByLogin(expertLogin);
		Team team = teamDAO.getTeamById(teamId);
		User expert = userDAO.getExpertForTeam(team);
		if (expert.getLogin() == null) {
			TeamInteract teamInteract = new TeamInteract(0, user,
					teamInteractDAO.getTeamInteractTypeByName("Join"), team, null);
			teamInteractDAO.addTeamInteract(teamInteract);
			factory.returnConnectionToPool();
		}
		factory.returnConnectionToPool();
	}
	
	public static void expertExitFromTeam(String expertLogin, int teamId) {
		User user;
		try {
			user = userDAO.getUserByLogin(expertLogin);
			List<Team> teamList = teamDAO.getTeamsForUser(user);
			Team team = teamDAO.getTeamById(teamId);
			if (teamList.size() != 0 && teamList.contains(team)) {
				TeamInteract teamInteract = new TeamInteract(0, user,
						teamInteractDAO.getTeamInteractTypeByName("Exit"), team, null);
				teamInteractDAO.addTeamInteract(teamInteract);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		factory.returnConnectionToPool();
	}

	public static void ejectExpertFromTeam(String capitanLogin, int teamId) throws SQLException {
		User user;
		user = userDAO.getUserByLogin(capitanLogin);
		List<Team> teamList = teamDAO.getTeamsForUser(user);
		Team team = teamDAO.getTeamById(teamId);
		if (teamList.size() != 0 && teamList.contains(team)) {
			User expert = userDAO.getExpertForTeam(team);
			TeamInteract teamInteract = new TeamInteract(0, expert,
					teamInteractDAO.getTeamInteractTypeByName("Expert_ejected"), team, null);
			teamInteractDAO.addTeamInteract(teamInteract);
		}
		factory.returnConnectionToPool();
	}
	
	public static void deleteMessage(String senderLogin, int messageId) throws SQLException {
		User user = userDAO.getUserByLogin(senderLogin);
		DeletedMessage deletedMessage = new DeletedMessage(0, user, new Message(messageId), null);
		deletedMessageDAO.addDeletedMessage(deletedMessage);
		factory.returnConnectionToPool();
	}
	
	public static void restoreMessage(int messageId) throws SQLException {
		DeletedMessage deletedMessage = new DeletedMessage(0, null, new Message(messageId), null);
		deletedMessageDAO.removeFromDeletedMessage(deletedMessage);
		factory.returnConnectionToPool();
	}
	
	public static void createSanction(String senderLogin, int userId, String sanctionName) throws SQLException {
		SanctionType sanctionType = sanctionDAO.getSanctionTypeByName(sanctionName);
		User sender = userDAO.getUserByLogin(senderLogin);
		Sanction sanction = new Sanction(0, sanctionType, sender, new User(userId), null, null);
		sanctionDAO.addSanction(sanction);
		factory.returnConnectionToPool();
	}
	
	public static void createUser(User newUser, String roleName, String senderLogin) throws SQLException {
		User tempUser = userDAO.getUserByLogin(newUser.getLogin());
		if (tempUser.getLogin() == null && (!newUser.getPassword().isEmpty())) {
			tempUser.setLogin(newUser.getLogin());
			tempUser.setPassword(newUser.getPassword());
			tempUser.setEmail(newUser.getEmail());
			tempUser.setName(newUser.getName());
			Role role = roleDAO.getRoleByName(roleName);
			User sender = userDAO.getUserByLogin(senderLogin);
			userDAO.addUser(tempUser);
			tempUser = userDAO.getUserByLogin(tempUser.getLogin());
			RoleAssigment roleAssigment = new RoleAssigment(0, role, sender, tempUser, null);
			roleAssigmentDAO.addRoleAssigment(roleAssigment);
			factory.returnConnectionToPool();
		}
		factory.returnConnectionToPool();
	}
	
	public static void saveUser(User newUserData) throws SQLException {
		User tempUser = userDAO.getUserByLogin(newUserData.getLogin());
		if (tempUser.getLogin() != null && (!newUserData.getPassword().isEmpty())) {
			tempUser.setPassword(newUserData.getPassword());
			tempUser.setEmail(newUserData.getEmail());
			tempUser.setName(newUserData.getName());
			// !!! Role do not changed!
			userDAO.updateUser(tempUser);
			factory.returnConnectionToPool();
		}
		factory.returnConnectionToPool();
	}
	
	public static Map.Entry<User, Role> findUser(String userLogin) throws SQLException {
		User user = userDAO.getUserByLogin(userLogin);
		if (user.getLogin() != null) {
			String tempLogin = user.getLogin();
			String tempPassword = user.getPassword();
			String tempName = user.getName();
			String tempEmail = user.getEmail();
			Role role = roleDAO.getUserRole(user);
			String tempRole = role.getName();
			factory.returnConnectionToPool();
			return Map.entry(user, role);
//			request.setAttribute("form_login", tempLogin);
//			request.setAttribute("form_password", tempPassword);
//			request.setAttribute("form_name", tempName);
//			request.setAttribute("form_email", tempEmail);
//			request.setAttribute("form_role", tempRole);
		}
		factory.returnConnectionToPool();
		return null;
	}

	public static void removeUser(String userLogin) throws SQLException {
		User tempUser = userDAO.getUserByLogin(userLogin);
		if (tempUser.getLogin() != null) {
			userDAO.deleteUser(tempUser);
		}
		factory.returnConnectionToPool();
	}
	
	
}