package ru.rsreu.kuznecovsokolov12.datalayer;

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
		factory.returnConnectionToPool();
	}
	
}
