package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;
import ru.rsreu.kuznecovsokolov12.servlet.ResourcerHolder;

public class OracleTeamDAO implements TeamDAO, ResourcerHolder {

	private final static String SQL_ALL_TEAMS_SELECT_WITH_CAP_AND_COUNT = resourser.getString("sql.team.select_all_teams_with_cap_and_captain");
	private final static String SQL_SELECT_TEAM_BY_NAME = resourser.getString("sql.team.select_team_by_name");
	private final static String SQL_SELECT_TEAM_BY_ID = resourser.getString("sql.team.select_team_by_id");
	private final static String SQL_SELECT_TEAMS_FOR_USER = resourser.getString("sql.team.select_teams_for_user");
	private final static String SQL_SELECT_TEAMS_CONSULTED_BY_EXPERT = resourser.getString("sql.team.select_teams_consulted_by_expert");
	private final static String SQL_SELECT_N_TEAMS_BEST_COOPERATED_EXPERT = resourser.getString("sql.team.select_n_teams_best_cooperated_expert");
	private final static String SQL_SELECT_TEAMS_EJECTED_EXPERT = resourser.getString("sql.team.select_team_which_ejected_expert");
	private static final String SQL_SELECT_COUNT_TEAM_MEMBERS = resourser.getString("sql.team.select_count_team_members");
	private static final String SQL_TEAM_CREATE = resourser.getString("sql.team.create");
	private static final String SQL_TEAM_DELETE = resourser.getString("sql.team.delete");
	
	public final static String COLUMN_TEAM_ID 		= resourser.getString("sql.team.column.id");
	public final static String COLUMN_TEAM_NAME 	= resourser.getString("sql.team.column.name");
	public final static String[] ALL_TEAM_COLUMNS = {COLUMN_TEAM_ID, COLUMN_TEAM_NAME};
	
	private Connection connection;

	public OracleTeamDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public Map<Team, Map<String, Integer>> getAllTeam() throws SQLException {
		PreparedStatement ps;
		Map<Team, Map<String, Integer>> result = new HashMap<>();
		ps = this.connection.prepareStatement(SQL_ALL_TEAMS_SELECT_WITH_CAP_AND_COUNT);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Team team = getTeamData(resultSet, ALL_TEAM_COLUMNS);
			String capitan_name = resultSet.getString(3);
			Integer count_members = resultSet.getInt(4);
			Map<String, Integer> map = new HashMap<>();
			map.put(capitan_name, count_members);
			result.put(team, map);
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
	
	@Override
	public List<Team> getTeamsConsultedByExpert(User expert) throws SQLException {
		PreparedStatement ps;
		List<Team> result = new ArrayList<>();
		ps = this.connection.prepareStatement(SQL_SELECT_TEAMS_CONSULTED_BY_EXPERT);
		ps.setInt(1, expert.getId());
		ps.setInt(2, expert.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Team team = getTeamData(resultSet, ALL_TEAM_COLUMNS);
			result.add(team);
		}
		return result;
	}

	@Override
	public Map<Team, Integer> getNTeamsBestCooperatedExpert(User expert, int N) throws SQLException {
		PreparedStatement ps;
		Map<Team, Integer> result = new HashMap<>();
		ps = this.connection.prepareStatement(SQL_SELECT_N_TEAMS_BEST_COOPERATED_EXPERT);
		ps.setInt(1, expert.getId());
		ps.setInt(2, N);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Team team = getTeamData(resultSet, ALL_TEAM_COLUMNS);
			int counts = resultSet.getInt("counts");
			result.put(team, counts);
		}
		return result;
	}
	
	@Override
	public int getCountTeamMembers(Team team) throws SQLException {
		PreparedStatement ps;
		int result = 0;
		ps = this.connection.prepareStatement(SQL_SELECT_COUNT_TEAM_MEMBERS);
		ps.setInt(1, team.getId());
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			result = resultSet.getInt("members");
		}
		return result;
	}
	
	@Override
	public void addTeam(Team team) throws SQLException {
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_TEAM_CREATE);
		ps.setString(1, team.getName());
		ps.executeUpdate();
	}
	
	@Override
	public void deleteTeam(Team team) throws SQLException {
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_TEAM_DELETE);
		ps.setInt(1, team.getId());
		ps.executeUpdate();
	}
	
	@Override
	public List<Team> getTeamsForUser(User user) throws SQLException {
		PreparedStatement ps;
		List<Team> result = new ArrayList<>();
		ps = this.connection.prepareStatement(SQL_SELECT_TEAMS_FOR_USER);
		ps.setInt(1, user.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Team team = getTeamData(resultSet, ALL_TEAM_COLUMNS);
			result.add(team);
		}
		return result;
	}

	@Override
	public List<Team> getTeamsEjectedExpert(User expert) throws SQLException {
		PreparedStatement ps;
		List<Team> result = new ArrayList<>();
		ps = this.connection.prepareStatement(SQL_SELECT_TEAMS_EJECTED_EXPERT);
		ps.setInt(1, expert.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Team team = getTeamData(resultSet, ALL_TEAM_COLUMNS);
			result.add(team);
		}
		return result;
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

	
}
