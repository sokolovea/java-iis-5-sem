package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.TeamInteractDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteract;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteractType;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;
import ru.rsreu.kuznecovsokolov12.servlet.ResourcerHolder;

public class OracleTeamInteractDAO implements TeamInteractDAO, ResourcerHolder {

	private final static String SQL_SELECT_TEAM_INTERACTS_BY_USER = resourser.getString("sql.team_interact.select_team_interacts_by_user");
	private final static String SQL_ALL_TEAM_INTERACTS_SELECT = resourser.getString("sql.team_interact.select_all_team_interacts");
	private static final String SQL_TEAM_INTERACT_CREATE = resourser.getString("sql.team_interact.create");
	private static final String SQL_TEAM_INTERACT_TYPE_BY_NAME = resourser.getString("sql.team_interact.select_team_interact_type_by_name");
	private static final String SQL_TEAM_INTERACT_DELETE_FOR_USER = resourser.getString("sql.team_interact.delete_for_user");
	
	public final static String COLUMN_TEAM_INTERACT_ID 	= resourser.getString("sql.team_interact.column.id");
	public final static String COLUMN_TEAM_INTERACT_TIME 	= resourser.getString("sql.team_interact.column.time");
	public final static String[] ALL_TEAM_INTERACT_COLUMNS = {COLUMN_TEAM_INTERACT_ID, COLUMN_TEAM_INTERACT_TIME};
	
	public final static String COLUMN_TEAM_INTERACT_TYPE_ID 	= resourser.getString("sql.team_interact_type.column.id");
	public final static String COLUMN_TEAM_INTERACT_TYPE_NAME 	= resourser.getString("sql.team_interact_type.column.name");
	public final static String[] ALL_TEAM_INTERACT_TYPE_COLUMNS = {COLUMN_TEAM_INTERACT_TYPE_ID, COLUMN_TEAM_INTERACT_TYPE_NAME};
	
	
	private Connection connection;

	public OracleTeamInteractDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<TeamInteract> getTeamInteractsForUser(User user) throws SQLException {
		PreparedStatement ps;
		List<TeamInteract> result = new ArrayList<TeamInteract>();
		ps = this.connection.prepareStatement(SQL_SELECT_TEAM_INTERACTS_BY_USER);
		ps.setInt(1, user.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			TeamInteract teamInteract = getTeamInteractData(resultSet, ALL_TEAM_INTERACT_COLUMNS);
			teamInteract.setType(getTeamInteractTypeData(resultSet, ALL_TEAM_INTERACT_TYPE_COLUMNS));
			teamInteract.setTeam(OracleTeamDAO.getTeamData(resultSet, OracleTeamDAO.ALL_TEAM_COLUMNS));
			teamInteract.setUser(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			result.add(teamInteract);
		}
		return result;
	}

	@Override
	public List<TeamInteract> getAllTeamInteracts() throws SQLException {
		PreparedStatement ps;
		List<TeamInteract> result = new ArrayList<TeamInteract>();
		ps = this.connection.prepareStatement(SQL_ALL_TEAM_INTERACTS_SELECT);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			TeamInteract teamInteract = getTeamInteractData(resultSet, ALL_TEAM_INTERACT_COLUMNS);
			result.add(teamInteract);
		}
		return result;
	}
	
	@Override
	public TeamInteractType getTeamInteractTypeByName(String name) throws SQLException {
		PreparedStatement ps;
		TeamInteractType result = new TeamInteractType();
		ps = this.connection.prepareStatement(SQL_TEAM_INTERACT_TYPE_BY_NAME);
		ps.setString(1, name);
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			result = getTeamInteractTypeData(resultSet, ALL_TEAM_INTERACT_TYPE_COLUMNS);
		}
		return result;
	}
	
	@Override
	public void addTeamInteract(TeamInteract teamInteract) throws SQLException {
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_TEAM_INTERACT_CREATE);
		ps.setInt(1, teamInteract.getUser().getId());
		ps.setInt(2, teamInteract.getType().getId());
		ps.setInt(3, teamInteract.getTeam().getId());
		ps.executeUpdate();
	}
	
	@Override
	public void deleteTeamInteractsForUser(User user) throws SQLException {
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_TEAM_INTERACT_DELETE_FOR_USER);
		ps.setInt(1, user.getId());
		ps.executeUpdate();
	}

	public static TeamInteract getTeamInteractData(ResultSet resultSet, String... columns) throws SQLException {
		TeamInteract teamInteract = new TeamInteract();
		for (String column : columns) {
			if (column.equals(COLUMN_TEAM_INTERACT_ID)) {
				teamInteract.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_TEAM_INTERACT_TIME)) {
				teamInteract.setTime(resultSet.getTimestamp(column));
				continue;
			}
		}
		return teamInteract;
	}
	
	public static TeamInteractType getTeamInteractTypeData(ResultSet resultSet, String... columns) throws SQLException {
		TeamInteractType teamInteractType = new TeamInteractType();
		for (String column : columns) {
			if (column.equals(COLUMN_TEAM_INTERACT_TYPE_ID)) {
				teamInteractType.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_TEAM_INTERACT_TYPE_NAME)) {
				teamInteractType.setName(resultSet.getString(column));
				continue;
			}
		}
		return teamInteractType;
	}


}
