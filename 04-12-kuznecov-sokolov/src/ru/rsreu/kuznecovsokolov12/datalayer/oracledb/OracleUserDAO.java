package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Role;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class OracleUserDAO implements UserDAO {

	private final static String SQL_USER_SELECT_BY_ID = "SELECT * FROM \"USER\" WHERE \"user_id\" = ?";
	private final static String SQL_USER_SELECT_BY_LOGIN = "SELECT * FROM \"USER\" WHERE \"login\" = ?";
	private static final String SQL_SELECT_EXPERT_FOR_TEAM = "select \"user_id\", \"login\" from (select DISTINCT \"user_id\", \"login\", \"team_id\", first_value(\"type_name\") over (PARTITION BY \"user_id\", \"login\", \"team_id\" order by \"TEAM_INTERACT\".\"time\" desc) \"last_interact\" from \"TEAM\" join \"TEAM_INTERACT\" on \"team_id\" = \"team\" join \"TEAM_INTERACT_TYPE\" on \"type\" = \"type_id\" join \"USER\" on \"user_id\" = \"user\" join \"ROLE_ASSIGMENT\" on \"user\" = \"receiver\" join \"ROLE\" on \"role_id\" = \"role\" where \"ROLE\".\"role_name\" = 'Expert' and \"team_id\" = ?) where \"last_interact\" = 'Join'";
	private final static String SQL_ALL_USERS_SELECT = "select * FROM \"USER\"";
	private static final String SQL_ALL_USERS_WITH_ROLE_SELECT = "select \"USER\".*, \"ROLE\".* from \"USER\" join \"ROLE_ASSIGMENT\" on \"user_id\" = \"receiver\" join \"ROLE\" on \"role\" = \"role_id\"";
	private final static String SQL_UNPRIVILEGED_USERS_SELECT = "select \"USER\".* from ((\"USER\" join \"ROLE_ASSIGMENT\" on \"USER\".\"user_id\" = \"ROLE_ASSIGMENT\".\"receiver\") join \"ROLE\" on \"ROLE_ASSIGMENT\".\"role\" = \"ROLE\".\"role_id\") join \"ROLE_GROUP\" on \"ROLE\".\"group\" = \"ROLE_GROUP\".\"role_group_id\" where \"ROLE_GROUP\".\"role_group_name\" = 'User'";
	private final static String SQL_BLOCKED_MORE_N_TIMES_USERS_SELECT = "select \"USER\".\"user_id\", \"login\", count(\"login\") count_blocks from (\"USER\" join \"SANCTION\" on \"USER\".\"user_id\" = \"SANCTION\".\"receiver\") join \"SANCTION_TYPE\" ON \"SANCTION_TYPE\".\"sanction_t_id\" = \"SANCTION\".\"type\" where \"SANCTION_TYPE\".\"sanction_t_name\" = 'Block' group by \"USER\".\"user_id\", \"login\" having count(\"login\") >= ?";
	private static final String SQL_TEAM_USER_LIST_SELECT = "select \"user_id\", \"login\" from (select DISTINCT \"user_id\", \"login\", \"team_id\", first_value(\"type_name\") over (PARTITION BY \"user_id\", \"login\", \"team_id\" order by \"TEAM_INTERACT\".\"time\" desc) \"last_interact\" from \"TEAM\" join \"TEAM_INTERACT\" on \"team_id\" = \"team\" join \"TEAM_INTERACT_TYPE\" on \"type\" = \"type_id\" join \"USER\" on \"user_id\" = \"user\" join \"ROLE_ASSIGMENT\" on \"user\" = \"receiver\" join \"ROLE\" on \"role_id\" = \"role\" where \"ROLE\".\"role_name\" = 'Common user' and \"team_id\" = ?) where \"last_interact\" = 'Join'";
	private final static String SQL_USER_UPDATE = "update \"USER\" set \"login\" = ?, \"password\" = ?, \"user_name\" = ?, \"email\" = ?, \"is_authorized\" = ? where \"USER\".\"user_id\" = ?";
	private final static String SQL_USER_CREATE = "INSERT INTO \"USER\" (\"login\", \"password\", \"user_name\", \"email\") VALUES (?, ?, ?, ?)";
	
	public final static String COLUMN_USER_ID 			  = "user_id";
	public final static String COLUMN_USER_LOGIN 		  = "login";
	public final static String COLUMN_USER_PASSWORD 	  = "password";
	public final static String COLUMN_USER_NAME 		  = "user_name";
	public final static String COLUMN_USER_EMAIL 	      = "email";
	public final static String COLUMN_USER_IS_AUTHORIZED  = "is_authorized";
	public final static String[] ALL_USER_COLUMNS = {COLUMN_USER_ID, COLUMN_USER_LOGIN, COLUMN_USER_PASSWORD, COLUMN_USER_NAME, COLUMN_USER_EMAIL, COLUMN_USER_IS_AUTHORIZED};
	
	
	
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
	public User getExpertForTeam(Team team) throws SQLException {
		User user = new User();
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_SELECT_EXPERT_FOR_TEAM);
		ps.setInt(1, team.getId());
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			user = getUserData(resultSet, COLUMN_USER_ID, COLUMN_USER_LOGIN);
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
	public Map<User, Role> getUsersWithRole() throws SQLException {
		PreparedStatement ps;
		Map<User, Role> result = new HashMap<>();
		ps = this.connection.prepareStatement(SQL_ALL_USERS_WITH_ROLE_SELECT);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			User user = getUserData(resultSet, ALL_USER_COLUMNS);
			Role role = OracleRoleDAO.getRoleData(resultSet, OracleRoleDAO.ALL_ROLE_COLUMNS);
			result.put(user, role);
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
	
	@Override
	public List<User> getUnprivilegedUsers() throws SQLException {
		PreparedStatement ps;
		List<User> result = new ArrayList<User>();
		ps = this.connection.prepareStatement(SQL_UNPRIVILEGED_USERS_SELECT);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			User user = getUserData(resultSet, ALL_USER_COLUMNS);
			result.add(user);
		}
		return result;
	}

	@Override
	public List<User> getBlockedUsersMoreNTimes(int N) throws SQLException {
		PreparedStatement ps;
		List<User> result = new ArrayList<User>();
		ps = this.connection.prepareStatement(SQL_BLOCKED_MORE_N_TIMES_USERS_SELECT);
		ps.setInt(1, N);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			User user = getUserData(resultSet, COLUMN_USER_ID, COLUMN_USER_LOGIN);
			result.add(user);
		}
		return result;
	}
	
	@Override
	public List<User> getTeamUserList(Team team) throws SQLException {
		PreparedStatement ps;
		List<User> result = new ArrayList<User>();
		ps = this.connection.prepareStatement(SQL_TEAM_USER_LIST_SELECT);
		ps.setInt(1, team.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			User user = getUserData(resultSet, COLUMN_USER_ID, COLUMN_USER_LOGIN);
			result.add(user);
		}
		return result;
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
	
	// Use this method only extreme cases!!!
	public static User getUserData(ResultSet resultSet, Integer... columns) throws SQLException {
		User user = new User();
		for (int i = 0; i < columns.length; i++) {
			
			if (columns[i] == null) {
				continue;
			}
			
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
