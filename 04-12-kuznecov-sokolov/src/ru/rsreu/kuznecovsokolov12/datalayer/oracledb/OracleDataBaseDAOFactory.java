package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
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

public class OracleDataBaseDAOFactory extends DAOFactory {
	private static volatile OracleDataBaseDAOFactory instance;
	private Connection connection;
	private static int count = 0;

	private OracleDataBaseDAOFactory() {
	}

	public static OracleDataBaseDAOFactory getInstance() throws ClassNotFoundException, SQLException {
		OracleDataBaseDAOFactory factory = instance;
		if (instance == null) {
			synchronized (OracleDataBaseDAOFactory.class) {
				instance = factory;
				factory = new OracleDataBaseDAOFactory();
				factory.connected();
			}
		}
		return factory;
	}

	private void connected() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		DataSource ds;
		Context envCtx;
		try {
			envCtx = (Context) (new InitialContext().lookup("java:comp/env"));
			ds = (DataSource) envCtx.lookup("jdbc/database");
			connection = ds.getConnection();
			count += 1;
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserDAO getUserDAO() {
		return new OracleUserDAO(connection);
	}

	@Override
	public RoleDAO getRoleDAO() {
		return new OracleRoleDAO(connection);
	}
	
	@Override
	public SettingDAO getSettingDAO() {
		return new OracleSettingDAO(connection);
	}
	
	@Override
	public TeamDAO getTeamDAO() {
		return new OracleTeamDAO(connection);
	}

	@Override
	public TeamInteractDAO getTeamInteractDAO() {
		return new OracleTeamInteractDAO(connection);
	}

	@Override
	public RoleAssigmentDAO getRoleAssigmentDAO() {
		return new OracleRoleAssigmentDAO(connection);
	}

	@Override
	public MessageDAO getMessageDAO() {
		return new OracleMessageDAO(connection);
	}
	
	@Override
	public DeletedMessageDAO getDeletedMessageDAO() {
		return new OracleDeletedMessageDAO(connection);
	}
	
	@Override
	public SanctionDAO getSanctionDAO() {
		return new OracleSanctionDAO(connection);
	}
	
	@Override
	public MessageAttachingDAO getMessageAttachingDAO() {
		return new OracleMessageAttachingDAO(connection);
	}

	@Override
	public void returnConnectionToPool() {
		try {
			this.connection.close();
			count -= 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
