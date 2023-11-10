package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.Connection;
import java.sql.SQLException;

import jdk.jshell.UnresolvedReferenceException;
import ru.rsreu.kuznecovsokolov12.datalayer.data.*;
import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.RoleDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
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
		SettingDAO settingDAO = factory.getSettingDAO();
		Setting settingTeamCapacity = new Setting(1, "max_team_capacity", Integer.parseInt(teamCapacity));
		Setting settingExpertCapacity = new Setting(2, "max_team_consulted_expert", Integer.parseInt(expertCapacity));
		settingDAO.setSetting(settingTeamCapacity);
		settingDAO.setSetting(settingExpertCapacity);
		factory.returnConnectionToPool();
		
	}
}