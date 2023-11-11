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

public class OracleTeamDAO implements TeamDAO {

	private final static String SQL_ALL_TEAMS_SELECT_WITH_CAP_AND_COUNT = "TODO";
	private final static String SQL_SELECT_TEAM_BY_NAME = "select * from \"TEAM\" where \"team_name\" = ?";
	private final static String SQL_SELECT_TEAM_BY_ID = "select * from \"TEAM\" where \"team_id\" = ?";
	private final static String SQL_SELECT_TEAMS_CONSULTED_BY_EXPERT = "select distinct \"TEAM\".* from \"TEAM\" join \"TEAM_INTERACT\" on \"team_id\" = \"team\" join \"TEAM_INTERACT_TYPE\" on \"type\" = \"type_id\" join \"USER\" on \"user_id\" = \"user\" join \"ROLE_ASSIGMENT\" on \"user\" = \"receiver\" join \"ROLE\" on \"role_id\" = \"role\" where \"ROLE\".\"role_name\" = 'Expert' and \"USER\".\"user_id\" = ?";
	private final static String SQL_SELECT_N_TEAMS_BEST_COOPERATED_EXPERT = "select \"TEAM\".*, count(\"MESSAGE\".\"author\") as counts from \"TEAM\" join \"MESSAGE_ATTACHING\" on \"team_id\" = \"team\" join \"MESSAGE\" on \"message_id\" = \"message\" join \"USER\" on \"user_id\" = \"author\" join \"ROLE_ASSIGMENT\" on \"user_id\" = \"receiver\" join \"ROLE\" on \"role_id\" = \"role\" where \"ROLE\".\"role_name\" = 'Expert' and \"USER\".\"user_id\" = ? and rownum <= ? group by \"TEAM\".\"team_id\", \"TEAM\".\"team_name\" order by counts desc";
	private final static String SQL_SELECT_TEAMS_EJECTED_EXPERT = "select distinct \"TEAM\".* from \"TEAM\" join \"TEAM_INTERACT\" on \"team_id\" = \"team\" join \"TEAM_INTERACT_TYPE\" on \"type\" = \"type_id\" join \"USER\" on \"user_id\" = \"user\" join \"ROLE_ASSIGMENT\" on \"user\" = \"receiver\" join \"ROLE\" on \"role_id\" = \"role\" where \"TEAM_INTERACT_TYPE\".\"type_name\" = 'Expert_ejected' and \"ROLE\".\"role_name\" = 'Expert' and \"USER\".\"user_id\" = ?";
	
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
	public Map<Team, Map<String, Integer>> getAllTeam() throws SQLException {
		PreparedStatement ps;
		Map<Team, Map<String, Integer>> result = new HashMap<>();
		ps = this.connection.prepareStatement(SQL_ALL_TEAMS_SELECT_WITH_CAP_AND_COUNT);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Team team = getTeamData(resultSet, ALL_TEAM_COLUMNS);
			String capitan_name = resultSet.getString("capitan_name");
			Integer count_members = resultSet.getInt("count_members");
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
