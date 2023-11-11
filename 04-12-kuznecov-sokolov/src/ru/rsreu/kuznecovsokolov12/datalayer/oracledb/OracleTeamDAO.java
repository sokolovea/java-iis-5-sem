package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class OracleTeamDAO implements TeamDAO {

	private final static String SQL_ALL_TEAMS_SELECT = "TODO";
	private final static String SQL_SELECT_TEAM_BY_NAME = "TODO";
	private final static String SQL_SELECT_TEAM_BY_ID = "TODO";
	
	public final static String COLUMN_TEAM_ID 		= "team_id";
	public final static String COLUMN_TEAM_NAME 	= "team_name";
	public final static String[] ALL_TEAM_COLUMNS = {COLUMN_TEAM_ID, COLUMN_TEAM_NAME};
	
	private Connection connection;

	public OracleTeamDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<Team> getAllTeam() throws SQLException {
		PreparedStatement ps;
		List<Team> result = new ArrayList<Team>();
		ps = this.connection.prepareStatement(SQL_ALL_TEAMS_SELECT);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Team team = getTeamData(resultSet, ALL_TEAM_COLUMNS);
			result.add(team);
		}
		return result;
	}

	@Override
	public Team getTeamByName(String name) throws SQLException {
		Team team = new Team();
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_SELECT_TEAM_BY_NAME);
		ps.setString(1, name);
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			team = getTeamData(resultSet, ALL_TEAM_COLUMNS);
		}
		return team;
	}

	@Override
	public Team getTeamById(int id) throws SQLException {
		Team team = new Team();
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_SELECT_TEAM_BY_ID);
		ps.setInt(1, id);
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			team = getTeamData(resultSet, ALL_TEAM_COLUMNS);
		}
		return team;
	}
	
	public static Team getTeamData(ResultSet resultSet, String... columns) throws SQLException {
		Team team = new Team();
		for (String column : columns) {
			if (column.equals(COLUMN_TEAM_ID)) {
				team.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_TEAM_NAME)) {
				team.setName(resultSet.getString(column));
				continue;
			}
		}
		return team;
	}

	@Override
	public List<Team> getTeamsConsultedByExpert(User expert) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Team> getNTeamsBestCooperatedExpert(User expert, int N) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Team> getTeamsEjectedExpert(User expert) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
