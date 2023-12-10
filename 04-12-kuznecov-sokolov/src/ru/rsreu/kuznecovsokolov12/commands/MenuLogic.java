package ru.rsreu.kuznecovsokolov12.commands;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOAcces;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.MessageAttaching;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Role;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Setting;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteract;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;
import ru.rsreu.kuznecovsokolov12.exceptions.RedirectErrorPage;
import ru.rsreu.kuznecovsokolov12.utils.ConfigurationManager;

public class MenuLogic extends DAOAcces {

	public static void fillTeamPageForUser(HttpServletRequest request, String login, int teamId) throws RedirectErrorPage {
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
				String url = ConfigurationManager.getProperty("path.page.team_select");
				throw new RedirectErrorPage(url);
			}
			teamExpert = userDAO.getExpertForTeam(team);
			teamMembers = userDAO.getTeamUserList(team);
			User captain = userDAO.getTeamCapitan(team);
			teamMembers.remove(captain);
			teamMembers.add(0, captain);
			messageList = messageDAO.getAllMessagesForTeam(team);
			deletedMessageSet = messageDAO.getDeletedMessagesForTeam(team);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("messageList", messageList);
		request.setAttribute("deletedMessageSet", deletedMessageSet);
		request.setAttribute("team", team);
		request.setAttribute("teamMembers", teamMembers);
		request.setAttribute("teamExpert", teamExpert);
	}

	public static void fillTeamSelectPageForUser(HttpServletRequest request, String login) {
		HttpSession session = request.getSession(false);
		List<Team> teamList = null;
		Map<Team, Map<String, Integer>> fullTeamMap = null;
		List<Setting> settingList = null;
		try {
			User user = userDAO.getUserByLogin(login);
			teamList = teamDAO.getTeamsForUser(user);
			if ((teamList != null) && (teamList.size() != 0)) {
				session.setAttribute(MenuCommand.PARAM_TEAM_ID, teamList.get(0).getId());
			}
			fullTeamMap = teamDAO.getAllTeam();
			settingList = settingDAO.getSetting();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("fullTeamMap", fullTeamMap);
		request.setAttribute("teamList", teamList);
		request.setAttribute("team_capacity", settingList.get(0).getValue());
	}

	public static void fillMainPageForModerator(HttpServletRequest request) {
		List<MessageAttaching> messageList = null;
		Set<MessageAttaching> deletedMessageSet = null;
		List<User> userList = null;
		Map<Team, Map<String, Integer>> fullTeamMap = null;
		Set<User> blockedUsers = null;
		try {
			fullTeamMap = teamDAO.getAllTeam();
			userList = userDAO.getUnprivilegedUsers();
			messageList = messageAttachDAO.getAllMessageAttachs();
			deletedMessageSet = messageAttachDAO.getAllDeletedMessageAttachs();
			blockedUsers = userDAO.getBlockedUsers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("user_list", userList);
		request.setAttribute("messageList", messageList);
		request.setAttribute("deletedMessageSet", deletedMessageSet);
		request.setAttribute("fullTeamMap", fullTeamMap);
		request.setAttribute("blockedUsers", blockedUsers);
	}

	public static void fillMainPageForAdmin(HttpServletRequest request) {
		try {
			List<User> userList = userDAO.getUsers();
			List<Role> roleList = roleDAO.getAllRoles();
			Set<User> blockedUsers = userDAO.getBlockedUsers();
			
			request.setAttribute("user_list", userList);
			request.setAttribute("roleList", roleList);
			request.setAttribute("blockedUsers", blockedUsers);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void fillSettingsPageForAdmin(HttpServletRequest request) {
		try {
			List<Setting> settingList = settingDAO.getSetting();
			request.setAttribute("setting_list", settingList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void userExitFromTeam(String login) {
		User user;
		try {
			user = userDAO.getUserByLogin(login);
			List<Team> teamList = teamDAO.getTeamsForUser(user);
			if (teamList.size() != 0) {
				TeamInteract teamInteract = new TeamInteract(0, user,
						teamInteractDAO.getTeamInteractTypeByName("Exit"), teamList.get(0), null);
				teamInteractDAO.addTeamInteract(teamInteract);
				int countMembers = teamDAO.getCountTeamMembers(teamList.get(0));
				if (countMembers == 1) {
					teamDAO.deleteTeam(teamList.get(0));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
