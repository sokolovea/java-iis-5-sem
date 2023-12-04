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
import ru.rsreu.kuznecovsokolov12.servlet.ResourcerHolder;

public class OracleUserDAO implements UserDAO, ResourcerHolder {

	private final static String SQL_USER_SELECT_BY_ID = resourser.getString("sql.user.select_by_id");
	private final static String SQL_USER_SELECT_BY_LOGIN = resourser.getString("sql.user.select_by_login");
	private static final String SQL_SELECT_EXPERT_FOR_TEAM = resourser.getString("sql.user.select_expert_for_team");
	private final static String SQL_ALL_USERS_SELECT = resourser.getString("sql.user.select_all_users");
	private static final String SQL_ALL_USERS_WITH_ROLE_SELECT = resourser.getString("sql.user.select_all_users_with_role");
	private final static String SQL_UNPRIVILEGED_USERS_SELECT = resourser.getString("sql.user.select_unprivileged_users");
	private final static String SQL_BLOCKED_MORE_N_TIMES_USERS_SELECT = resourser.getString("sql.user.select_blocked_more_n_times_users");
	private static final String SQL_TEAM_USER_LIST_SELECT = resourser.getString("sql.user.select_team_user_list");
	private static final String SQL_TEAM_CAPITAN_SELECT = resourser.getString("sql.user.select_team_captain");
	
	private final static String SQL_USER_UPDATE = resourser.getString("sql.user.update_user");
	private final static String SQL_USER_CREATE = resourser.getString("sql.user.create_user");
	private static final String SQL_USER_DELETE = resourser.getString("sql.user.delete_user");
	
	public final static String COLUMN_USER_ID 			  = resourser.getString("sql.user.column.id");
	public final static String COLUMN_USER_LOGIN 		  = resourser.getString("sql.user.column.login");
	public final static String COLUMN_USER_PASSWORD 	  = resourser.getString("sql.user.column.password");
	public final static String COLUMN_USER_NAME 		  = resourser.getString("sql.user.column.name");
	public final static String COLUMN_USER_EMAIL 	      = resourser.getString("sql.user.column.email");
	public final static String COLUMN_USER_IS_AUTHORIZED  = resourser.getString("sql.user.column.is_auth");
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
	
	@Override
	public void deleteUser(User user) throws SQLException {
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_USER_DELETE);
		ps.setInt(1, user.getId());
		ps.executeUpdate();
	}
	
	@Override
	public User getTeamCapitan(Team team) throws SQLException {
		User user = new User();
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_TEAM_CAPITAN_SELECT);
		ps.setInt(1, team.getId());
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			user = getUserData(resultSet, ALL_USER_COLUMNS);
		}
		return user;
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
				String auth = resultSet.getString(column);
				if (auth != null) {
					user.setIsAuthorized(auth.equals("1"));	
				} else {
					user.setIsAuthorized(false);
				}
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
