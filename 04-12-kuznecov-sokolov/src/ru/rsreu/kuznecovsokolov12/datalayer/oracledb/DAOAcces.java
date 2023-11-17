package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

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

public class DAOAcces {

	public static DAOFactory factory;
	public static SettingDAO settingDAO;
	public static UserDAO userDAO;
	public static RoleDAO roleDAO;
	public static DeletedMessageDAO deletedMessageDAO;
	public static SanctionDAO sanctionDAO;
	public static MessageAttachingDAO messageAttachDAO;
	public static MessageDAO messageDAO;
	public static RoleAssigmentDAO roleAssigmentDAO;
	public static TeamDAO teamDAO;
	public static TeamInteractDAO teamInteractDAO;
	
	public static void initDAOitems() {
		System.out.print("from datalogic: ");
		factory = DAOFactory.getInstance(DBType.ORACLE);
		settingDAO = factory.getSettingDAO();
		userDAO = factory.getUserDAO();
		roleDAO = factory.getRoleDAO();
		deletedMessageDAO = factory.getDeletedMessageDAO();
		sanctionDAO = factory.getSanctionDAO();
		messageAttachDAO = factory.getMessageAttachingDAO();
		messageDAO = factory.getMessageDAO();
		roleAssigmentDAO = factory.getRoleAssigmentDAO();
		teamDAO = factory.getTeamDAO();
		teamInteractDAO = factory.getTeamInteractDAO();
	}
	
	public static void closeFactory() {
		System.out.print("from datalogic: ");
		factory.returnConnectionToPool();
	}
	
}
