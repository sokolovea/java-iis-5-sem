package ru.rsreu.kuznecovsokolov12.commands;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import ru.rsreu.kuznecovsokolov12.datalayer.data.*;
import ru.rsreu.kuznecovsokolov12.datalayer.DAOAcces;
import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.exceptions.RedirectErrorPage;
import ru.rsreu.kuznecovsokolov12.utils.ResourcerHolder;

public class DatabaseLogic extends DAOAcces implements ResourcerHolder {
	
	private static final String ERROR_CAPTAIN_TRY_CREATE_TEAM = resourser.getString("error.captain_try_create_team");
	private static final String ERROR_TEAM_NAME_ALREADY_EXIST = resourser.getString("error.team_name_already_exist");

	public static List<Setting> updateSetting(String teamCapacity, String expertCapacity) throws SQLException {
		Setting settingTeamCapacity = new Setting(ActionCommand.SETTING_MAX_TEAM_COUNT, Integer.parseInt(teamCapacity));
		Setting settingExpertCapacity = new Setting(ActionCommand.SETTING_MAX_TEAMS_FOR_EXPERT, Integer.parseInt(expertCapacity));
		settingDAO.setSetting(settingTeamCapacity);
		settingDAO.setSetting(settingExpertCapacity);
		return settingDAO.getSetting();
	}

	public static void sendMessage(String login, String messageData, int teamId) throws SQLException {
		User user = userDAO.getUserByLogin(login);
		List<Team> teamList = teamDAO.getTeamsForUser(user);
		Team userTeam = teamDAO.getTeamById(teamId);
		if (teamList.contains(userTeam)) {
			Message message = new Message();
			message.setAuthor(user);
			message.setData(messageData);
			MessageAttaching messageAttaching = new MessageAttaching();
			messageAttaching.setMessage(message);
			messageAttaching.setTeam(userTeam);
			messageAttachDAO.addMessage(messageAttaching);
		}
	}
		
	public static void createTeam(String userLogin, String teamName) throws SQLException, RedirectErrorPage {
		if (teamName != null && !teamName.isEmpty()) {
			User user = userDAO.getUserByLogin(userLogin);
			List<Team> teamList = teamDAO.getTeamsForUser(user);
			if (teamList.size() != 0) {
				if (LoginLogic.isCapitan(userLogin, teamList.get(0).getId())) {
					throw new RedirectErrorPage(null, ERROR_CAPTAIN_TRY_CREATE_TEAM);
				}
				TeamInteract teamInteract = new TeamInteract(0, user, teamInteractDAO.getTeamInteractTypeByName(ActionCommand.TEAM_INTERACT_TYPE_EXIT),
						teamList.get(0), null);
				teamInteractDAO.addTeamInteract(teamInteract);
			}
			Team teamExisted = teamDAO.getTeamByName(teamName);
			if (teamExisted.getName() != null) {
				throw new RedirectErrorPage(null, String.format(ERROR_TEAM_NAME_ALREADY_EXIST, teamName));
			}
			Team team = new Team();
			team.setName(teamName);
			teamDAO.addTeam(team);
			team = teamDAO.getTeamByName(teamName);
			TeamInteract teamInteract = new TeamInteract(0, user, teamInteractDAO.getTeamInteractTypeByName(ActionCommand.TEAM_INTERACT_TYPE_JOIN),
					team, null);
			teamInteractDAO.addTeamInteract(teamInteract);
		}
	}

	public static void userJoinTeam(String userLogin, int teamId) throws SQLException {
		User user = userDAO.getUserByLogin(userLogin);
		List<Team> teamList = teamDAO.getTeamsForUser(user);
		if (teamList.size() != 0) {
			if (LoginLogic.isCapitan(userLogin, teamList.get(0).getId()) || (teamList.get(0).getId() == teamId)) {
				return;
			}
			TeamInteract teamInteract = new TeamInteract(0, user, teamInteractDAO.getTeamInteractTypeByName(ActionCommand.TEAM_INTERACT_TYPE_EXIT),
					teamList.get(0), null);
			teamInteractDAO.addTeamInteract(teamInteract);
		}
		Team team = teamDAO.getTeamById(teamId);
		
		SettingDAO settingDAO = factory.getSettingDAO();
		int team_capacity = settingDAO.getSetting().get(0).getValue();
		int joinTeamMembers = teamDAO.getCountTeamMembers(team);
		if (joinTeamMembers < team_capacity) {
			TeamInteract teamInteract = new TeamInteract(0, user, teamInteractDAO.getTeamInteractTypeByName(ActionCommand.TEAM_INTERACT_TYPE_JOIN), team,
					null);
			teamInteractDAO.addTeamInteract(teamInteract);
		}
	}

	public static void expertJoinTeam(String expertLogin, int teamId) throws SQLException {
		User user = userDAO.getUserByLogin(expertLogin);
		Team team = teamDAO.getTeamById(teamId);
		User expertForTeam = userDAO.getExpertForTeam(team);
		int expertCapacity = settingDAO.getSetting().get(1).getValue();
		User expert = userDAO.getUserByLogin(expertLogin);
		int currentExpertTeamsCount = teamDAO.getTeamsConsultedByExpert(expert).size();
		if ((expertForTeam.getLogin() == null) && (expertCapacity > currentExpertTeamsCount)) {
			TeamInteract teamInteract = new TeamInteract(0, user, teamInteractDAO.getTeamInteractTypeByName(ActionCommand.TEAM_INTERACT_TYPE_JOIN),
					team, null);
			teamInteractDAO.addTeamInteract(teamInteract);
		}
	}

	public static void expertExitFromTeam(String expertLogin, int teamId) {
		User user;
		try {
			user = userDAO.getUserByLogin(expertLogin);
			List<Team> teamList = teamDAO.getTeamsForUser(user);
			Team team = teamDAO.getTeamById(teamId);
			if (teamList.size() != 0 && teamList.contains(team)) {
				TeamInteract teamInteract = new TeamInteract(0, user, teamInteractDAO.getTeamInteractTypeByName(ActionCommand.TEAM_INTERACT_TYPE_EXIT),
						team, null);
				teamInteractDAO.addTeamInteract(teamInteract);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void ejectExpertFromTeam(String capitanLogin, int teamId) throws SQLException {
		User user;
		user = userDAO.getUserByLogin(capitanLogin);
		List<Team> teamList = teamDAO.getTeamsForUser(user);
		Team team = teamDAO.getTeamById(teamId);
		if (teamList.size() != 0 && teamList.contains(team)) {
			User expert = userDAO.getExpertForTeam(team);
			if (expert.getLogin() != null) {
				TeamInteract teamInteract = new TeamInteract(0, expert,
						teamInteractDAO.getTeamInteractTypeByName(ActionCommand.TEAM_INTERACT_TYPE_EXPERT_EJECTED), team, null);
				teamInteractDAO.addTeamInteract(teamInteract);
			}
		}
	}

	public static void deleteMessage(String senderLogin, int messageId) throws SQLException {
		User user = userDAO.getUserByLogin(senderLogin);
		DeletedMessage deletedMessage = new DeletedMessage(0, user, new Message(messageId), null);
		deletedMessageDAO.addDeletedMessage(deletedMessage);
	}

	public static void restoreMessage(int messageId) throws SQLException {
		DeletedMessage deletedMessage = new DeletedMessage(0, null, new Message(messageId), null);
		deletedMessageDAO.removeFromDeletedMessage(deletedMessage);
	}

	public static void createSanction(String senderLogin, int userId, String sanctionName, String reason)
			throws SQLException {
		SanctionType sanctionType = sanctionDAO.getSanctionTypeByName(sanctionName);
		User sender = userDAO.getUserByLogin(senderLogin);
		Sanction sanction = new Sanction(0, sanctionType, sender, new User(userId), reason, null);
		sanctionDAO.addSanction(sanction);
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
		}
	}
	
	public static void saveUser(User newUserData, String role, String adminLogin) throws SQLException {
		User tempUser = userDAO.getUserByLogin(newUserData.getLogin());
		if (tempUser.getLogin() != null && (!newUserData.getPassword().isEmpty())) {
			tempUser.setPassword(newUserData.getPassword());
			tempUser.setEmail(newUserData.getEmail());
			tempUser.setName(newUserData.getName());
			userDAO.updateUser(tempUser);
			newUserData = userDAO.getUserByLogin(tempUser.getLogin());
			
			Role newRole = roleDAO.getRoleByName(role);
			Role currentRole = roleDAO.getUserRole(newUserData);
			if (newRole != currentRole) {
				
				if (currentRole.getName().equals(ActionCommand.ROLE_EXPERT)) {
					List<Team> expertTeams = teamDAO.getTeamsConsultedByExpert(newUserData);
					for (Team consulted_team : expertTeams) {
						TeamInteract teamInteract = new TeamInteract(0, newUserData, teamInteractDAO.getTeamInteractTypeByName(ActionCommand.TEAM_INTERACT_TYPE_EXIT),
								consulted_team, null);
						teamInteractDAO.addTeamInteract(teamInteract);
					}
				}
				
				if (currentRole.getName().equals(ActionCommand.ROLE_USER)) {
					List<Team> userTeams = teamDAO.getTeamsForUser(newUserData);
					for (Team userTeam : userTeams) {
						if (LoginLogic.isCapitan(newUserData.getLogin(), userTeam.getId())) {
							teamInteractDAO.deleteTeamInteractsForUser(newUserData);
						}
						else {
							TeamInteract teamInteract = new TeamInteract(0, newUserData, teamInteractDAO.getTeamInteractTypeByName(ActionCommand.TEAM_INTERACT_TYPE_EXIT),
									userTeam, null);
							teamInteractDAO.addTeamInteract(teamInteract);
						}
						
					}
				}
				
				User admin = userDAO.getUserByLogin(adminLogin);
				RoleAssigment roleAssigment = new RoleAssigment(-1, newRole, admin, newUserData, null);
				roleAssigmentDAO.addRoleAssigment(roleAssigment);
			}
		
		}
	}

	public static Map.Entry<User, Role> findUser(String userLogin) throws SQLException {
		User user = userDAO.getUserByLogin(userLogin);
		if (user.getLogin() != null) {
			Role role = roleDAO.getUserRole(user);
			return Map.entry(user, role);
		}
		return null;
	}

	public static void removeUser(String userLogin) throws SQLException {
		User tempUser = userDAO.getUserByLogin(userLogin);
		if (tempUser.getLogin() != null) {
			List<Team> teamList = teamDAO.getTeamsForUser(tempUser);
			
			userDAO.deleteUser(tempUser);
			
			if (teamList.size() != 0) {
				int countMembers = teamDAO.getCountTeamMembers(teamList.get(0));
				if (countMembers <= 1) {
					teamDAO.deleteTeam(teamList.get(0));
				}
			}
		}
	}

}