package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.TeamInteractDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteract;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteractType;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class OracleTeamInteractDAO implements TeamInteractDAO {

	private final static String SQL_SELECT_TEAM_INTERACT_BY_USER = "TODO";
	
	private final static String COLUMN_TEAM_INTERACT_ID 	= "team_interact_id";
	private final static String COLUMN_TEAM_INTERACT_TIME 	= "time";
	private final static String[] ALL_TEAM_INTERACT_COLUMNS = {COLUMN_TEAM_INTERACT_ID, COLUMN_TEAM_INTERACT_TIME};
	
	private final static String COLUMN_TEAM_INTERACT_TYPE_ID 	= "type_id";
	private final static String COLUMN_TEAM_INTERACT_TYPE_NAME 	= "type_name";
	private final static String[] ALL_TEAM_INTERACT_TYPE_COLUMNS = {COLUMN_TEAM_INTERACT_TYPE_ID, COLUMN_TEAM_INTERACT_TYPE_NAME};
	
	
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
		ps = this.connection.prepareStatement(SQL_SELECT_TEAM_INTERACT_BY_USER);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			TeamInteract teamInteract = getTeamInteractData(resultSet, ALL_TEAM_INTERACT_COLUMNS);
			result.add(teamInteract);
		}
		return result;
	}

	@Override
	public List<TeamInteract> getAllTeamInteracts() throws SQLException {
		// TODO Auto-generated method stub
		return null;
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
