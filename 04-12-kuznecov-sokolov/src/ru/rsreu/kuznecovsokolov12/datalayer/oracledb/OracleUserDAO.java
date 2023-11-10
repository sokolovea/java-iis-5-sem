package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class OracleUserDAO implements UserDAO {

	private final static String SQL_USER_SELECT_BY_ID = "SELECT * FROM \"USER\" WHERE \"id\" = ?";
	private final static String SQL_USER_SELECT_BY_LOGIN = "SELECT * FROM \"USER\", \"MESSAGE\" WHERE \"login\" = ?";
	private final static String SQL_ALL_USERS_SELECT = "select * FROM \"SETTING\"";
	private final static String SQL_USER_UPDATE = "update \"USER\" set \"login\" = ?, \"password\" = ?, \"is_authorized\" = ?, \"name\" = ?, \"email\" = ? where \"USER\".\"id\" = ?";
	private final static String SQL_USER_CREATE = "INSERT INTO \"USER\" (\"login\", \"password\", \"name\", \"email\") VALUES (?, ?, ?, ?)";
	
	private final static String COLUMN_USER_ID 			  = "ID";
	private final static String COLUMN_USER_LOGIN 		  = "LOGIN";
	private final static String COLUMN_USER_PASSWORD 	  = "PASSWORD";
	private final static String COLUMN_USER_NAME 		  = "NAME";
	private final static String COLUMN_USER_EMAIL 	      = "EMAIL";
	private final static String COLUMN_USER_IS_AUTHORIZED = "IS_AUTHORIZED";
	private final static String[] ALL_USER_COLUMNS = {COLUMN_USER_ID, COLUMN_USER_LOGIN, COLUMN_USER_PASSWORD, COLUMN_USER_NAME, COLUMN_USER_EMAIL, COLUMN_USER_IS_AUTHORIZED};
	
	
	
	private Connection connection;

	public OracleUserDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public User getUserByID(int id) throws SQLException {
		User user = new User();
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_USER_SELECT_BY_ID);
		ps.setInt(1, id);
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			user = getUserData(resultSet, ALL_USER_COLUMNS);
		}
		return user;
	}

	@Override
	public User getUserByLogin(String login) throws SQLException {
		User user = new User();
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_USER_SELECT_BY_LOGIN);
		ps.setString(1, login);
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			user = getUserData(resultSet, ALL_USER_COLUMNS);
		}
		return user;
	}

	@Override
	public List<User> getUsers() throws SQLException {
		PreparedStatement ps;
		List<User> result = new ArrayList<User>();
		ps = this.connection.prepareStatement(SQL_ALL_USERS_SELECT);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			User user = getUserData(resultSet, ALL_USER_COLUMNS);
			result.add(user);
		}
		return result;
	}

	@Override
	public void addUser(User user) throws SQLException {
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_USER_CREATE);
		ps.setString(1, user.getLogin());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getName());
		ps.setString(4, user.getEmail());
		ps.executeUpdate();
	}

	@Override
	public void updateUser(User user) throws SQLException {
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_USER_UPDATE);
		ps.setInt(6, user.getId());
		ps.setString(1, user.getLogin());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getName());
		ps.setString(4, user.getEmail());
		if (user.isIsAuthorized()) {
			ps.setString(5, "1");
		}
		else {
			ps.setString(5, "0");
		}
		ps.executeUpdate();
	}
	
	public static User getUserData(ResultSet resultSet, String... columns) throws SQLException {
		User user = new User();
		for (String column : columns) {
			if (column.equals(COLUMN_USER_ID)) {
				user.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_USER_LOGIN)) {
				user.setLogin(resultSet.getString(column));
				continue;
			}
			
			if (column.equals(COLUMN_USER_PASSWORD)) {
				user.setPassword(resultSet.getString(column));
				continue;
			}
			
			if (column.equals(COLUMN_USER_NAME)) {
				user.setName(resultSet.getString(column));
				continue;
			}
			
			if (column.equals(COLUMN_USER_EMAIL)) {
				user.setEmail(resultSet.getString(column));
				continue;
			}
			
			if (column.equals(COLUMN_USER_IS_AUTHORIZED)) {
				user.setIsAuthorized(resultSet.getString(column).equals("1"));
				continue;
			}
		}
		return user;
	}
	
	public static User getUserData(ResultSet resultSet, int... columns) throws SQLException {
		User user = new User();
		for (int i = 0; i < columns.length; i++) {
			if (i == 0) {
				user.setId(resultSet.getInt(columns[i]));
				continue;
			}
			
			if (i == 1) {
				user.setLogin(resultSet.getString(columns[i]));
				continue;
			}
			
			if (i == 2) {
				user.setPassword(resultSet.getString(columns[i]));
				continue;
			}
			
			if (i == 3) {
				user.setName(resultSet.getString(columns[i]));
				continue;
			}
			
			if (i == 4) {
				user.setEmail(resultSet.getString(columns[i]));
				continue;
			}
			
			if (i == 5) {
				user.setIsAuthorized(resultSet.getString(columns[i]).equals("1"));
				continue;
			}
		}
		return user;
	}
	
}
