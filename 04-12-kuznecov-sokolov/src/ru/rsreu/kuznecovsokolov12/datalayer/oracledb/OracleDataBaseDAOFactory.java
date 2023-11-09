package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;
import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.RoleDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;

public class OracleDataBaseDAOFactory extends DAOFactory {
	private static volatile OracleDataBaseDAOFactory instance;
	private Connection connection;

	private OracleDataBaseDAOFactory() {
	}

	public static OracleDataBaseDAOFactory getInstance() throws ClassNotFoundException, SQLException {
		OracleDataBaseDAOFactory factory = instance;
		if (instance == null) {
//			System.out.println("Create new Fabric!");
			synchronized (OracleDataBaseDAOFactory.class) {
				instance = factory;
				factory = new OracleDataBaseDAOFactory();
				factory.connected();
			}
		}
		return factory;
	}

	private void connected() throws ClassNotFoundException, SQLException {
//		Locale.setDefault(Locale.ENGLISH);
//		String url = "jdbc:oracle:thin:@localhost:1521:XE";
//		String user = "SYSTEM";
//		String password = "1";
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		System.out.println("BEFORE CONNECTION!");
//		connection = DriverManager.getConnection(url, user, password);
//		if (connection != null) {
//			System.out.println("CONNECTED!");
//		}
//		else {
//			System.out.println("FAILED TO CONNECT!");
//		}
		Class.forName("oracle.jdbc.driver.OracleDriver");
		DataSource ds;
		Context envCtx;
		try {
			envCtx = (Context) (new InitialContext().lookup("java:comp/env"));
			ds = (DataSource) envCtx.lookup("jdbc/database");
			connection = ds.getConnection();
		} catch (NamingException e) {
			System.out.println("CRITICAL DATASOURCE EXCEPTION!");
		}
		
//		if (ods == null) {
//			System.out.println("Create new ODS!");
//		}
//		ods.setURL("jdbc:oracle:thin:@localhost:1521:XE");
//		ods.setUser("SYSTEM");
//		ods.setPassword("1");
////		ods.setConnectionCacheName(CACHE_NAME);
//		Properties cacheProps = new Properties();
//		cacheProps.setProperty("MinLimit", "0");
//		cacheProps.setProperty("MaxLimit", "4");
//		cacheProps.setProperty("InitialLimit", "1");
//		cacheProps.setProperty("ConnectionWaitTimeout", "5");
//		cacheProps.setProperty("ValidateConnection", "true");
//		ods.setConnectionProperties(cacheProps);
//		connection = ods.getConnection();
//		if (connection != null) {
//			System.out.println("CONNECTED!");
//		}
//		else {
//			System.out.println("FAILED TO CONNECT!");
//		}
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
	public void returnConnectionToPool() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
