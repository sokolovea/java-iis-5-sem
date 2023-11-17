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

public interface DAOAcces {

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
	
}
