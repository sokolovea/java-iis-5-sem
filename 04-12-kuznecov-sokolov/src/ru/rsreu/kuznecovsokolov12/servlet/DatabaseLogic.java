package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jdk.jshell.UnresolvedReferenceException;
import ru.rsreu.kuznecovsokolov12.datalayer.data.*;
import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageAttachingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.RoleDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.oracledb.OracleDataBaseDAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.oracledb.OracleUserDAO;

public class DatabaseLogic {
	
//	private final static String ADMIN_LOGIN = "admin";
//	private final static String ADMIN_PASS = "1";
//	
//	private final static String MODERATOR_LOGIN = "moderator";
//	private final static String MODERATOR_PASS = "1";
//	
//	private final static String EXPERT_LOGIN = "expert";
//	private final static String EXPERT_PASS = "1";
//	
//	private final static String CAPTAIN_LOGIN = "captain";
//	private final static String CAPTAIN_PASS = "1";
//	
//	private final static String USER_LOGIN = "user";
//	private final static String USER_PASS = "1";

	public static void updateSetting(String teamCapacity, String expertCapacity) throws SQLException {
		
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		try {
			SettingDAO settingDAO = factory.getSettingDAO();
			Setting settingTeamCapacity = new Setting("max_team_capacity", Integer.parseInt(teamCapacity));
			Setting settingExpertCapacity = new Setting("max_team_consulted_expert", Integer.parseInt(expertCapacity));
			settingDAO.setSetting(settingTeamCapacity);
			settingDAO.setSetting(settingExpertCapacity);
		} finally {
			factory.returnConnectionToPool();
//			System.out.println("������� ���������� � ���!");
		}
		
	}
	
	public static void sendMessage(String login, String messageData) throws SQLException {
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		UserDAO userDAO = factory.getUserDAO();
		TeamDAO teamDAO = factory.getTeamDAO();
		MessageAttachingDAO messageAttachingDAO = factory.getMessageAttachingDAO();
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
		messageAttachingDAO.addMessage(messageAttaching);
		factory.returnConnectionToPool();

	}
}