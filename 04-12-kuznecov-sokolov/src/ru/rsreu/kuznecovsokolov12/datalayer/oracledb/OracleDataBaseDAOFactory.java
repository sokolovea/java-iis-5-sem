package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
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
			synchronized (OracleDataBaseDAOFactory.class) {
				instance = factory;
				factory = new OracleDataBaseDAOFactory();
				factory.connected();
			}
		}
		return factory;
	}

	private void connected() throws ClassNotFoundException, SQLException {
		Locale.setDefault(Locale.ENGLISH);
//		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
//		String url = "SYSTEM@//localhost:1521:XE";
		String user = "SYSTEM";
		String password = "1";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("BEFORE CONNECTION!");
		connection = DriverManager.getConnection(url, user, password);
		if (connection != null) {
			System.out.println("CONNECTED!");
		}
		else {
			System.out.println("FAILED TO CONNECT!");
		}
	}

	@Override
	public UserDAO getUserDAO() {
		return new OracleUserDAO(connection);
	}

	@Override
	public SettingDAO getSettingDAO() {
		return new OracleSettingDAO(connection);
	}

}
