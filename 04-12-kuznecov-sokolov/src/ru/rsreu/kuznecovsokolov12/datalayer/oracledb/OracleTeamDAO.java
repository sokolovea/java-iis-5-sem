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

	private final static String SQL_ALL_TEAMS_SELECT_WITH_CAP_AND_COUNT = "select t1.\"team_id\", t1.\"team_name\", \"login\", \"MEMBERS\" from (select \"team_id\", \"team_name\", \"login\" from (select RANK() OVER (PARTITION BY \"team_id\" ORDER BY \"TEAM_INTERACT\".\"time\") \"RANK\", \"TEAM\".*, \"login\" from \"TEAM\" join \"TEAM_INTERACT\" on \"team_id\" = \"team\" join \"TEAM_INTERACT_TYPE\" on \"type\" = \"type_id\" join \"USER\" on \"user\" = \"user_id\" join \"ROLE_ASSIGMENT\" on \"receiver\" = \"user_id\" join \"ROLE\" on \"role\" = \"role_id\" where \"role_name\" = 'Common user' and \"type_name\" = 'Join') \"capitans_table\" where \"RANK\" = 1) t1 join (select \"team_id\", \"team_name\", case when \"count_exit\" is null then \"count_join\" else \"count_join\" - \"count_exit\" end members from (select \"team_id\", \"team_name\", count(distinct \"TEAM_INTERACT\".\"time\") \"count_join\" from \"TEAM\" join \"TEAM_INTERACT\" on \"team_id\" = \"team\" join \"TEAM_INTERACT_TYPE\" on \"type\" = \"type_id\" join \"USER\" on \"user\" = \"user_id\" join \"ROLE_ASSIGMENT\" on \"receiver\" = \"user_id\" join \"ROLE\" on \"role\" = \"role_id\" where \"role_name\" = 'Common user' and \"type_name\" = 'Join' group by \"team_id\", \"team_name\") t1 left join (select \"team_id\" \"team_id_2\", \"team_name\" \"team_name_2\", count(distinct \"TEAM_INTERACT\".\"time\") \"count_exit\" from \"TEAM\" join \"TEAM_INTERACT\" on \"team_id\" = \"team\" join \"TEAM_INTERACT_TYPE\" on \"type\" = \"type_id\" join \"USER\" on \"user\" = \"user_id\" join \"ROLE_ASSIGMENT\" on \"receiver\" = \"user_id\" join \"ROLE\" on \"role\" = \"role_id\" where \"role_name\" = 'Common user' and \"type_name\" = 'Exit' group by \"team_id\", \"team_name\") t2 on \"team_id_2\" = t1.\"team_id\") t2 on t1.\"team_id\"= t2.\"team_id\"";
	private final static String SQL_SELECT_TEAM_BY_NAME = "select * from \"TEAM\" where \"team_name\" = ?";
	private final static String SQL_SELECT_TEAM_BY_ID = "select * from \"TEAM\" where \"team_id\" = ?";
	private final static String SQL_SELECT_TEAMS_FOR_USER = "select \"team_id\", \"team_name\" from (select DISTINCT \"user_id\", \"login\", \"team_id\", \"team_name\", first_value(\"type_name\") over (PARTITION BY \"user_id\", \"login\", \"team_id\", \"team_name\" order by \"TEAM_INTERACT\".\"time\" desc) \"last_interact\" from \"TEAM\" join \"TEAM_INTERACT\" on \"team_id\" = \"team\" join \"TEAM_INTERACT_TYPE\" on \"type\" = \"type_id\" join \"USER\" on \"user_id\" = \"user\" join \"ROLE_ASSIGMENT\" on \"user\" = \"receiver\" join \"ROLE\" on \"role_id\" = \"role\" where \"user_id\" = ?) where \"last_interact\" = 'Join'";
	private final static String SQL_SELECT_TEAMS_CONSULTED_BY_EXPERT = "select distinct \"team_id\", \"team_name\" from (select \"team_id\", \"team_name\", count(distinct \"TEAM_INTERACT\".\"time\") \"count_join\" from \"TEAM\" join \"TEAM_INTERACT\" on \"team_id\" = \"team\" join \"TEAM_INTERACT_TYPE\" on \"type\" = \"type_id\" join \"USER\" on \"user\" = \"user_id\" join \"ROLE_ASSIGMENT\" on \"receiver\" = \"user_id\" join \"ROLE\" on \"role\" = \"role_id\" where \"role_name\" = 'Expert' and \"type_name\" = 'Join' and \"USER\".\"user_id\" = ? group by \"user_id\", \"team_id\", \"team_name\") t1 left join (select \"team_id\" \"team_id_2\", \"team_name\" \"team_name_2\", count(distinct \"TEAM_INTERACT\".\"time\") \"count_exit\" from \"TEAM\" join \"TEAM_INTERACT\" on \"team_id\" = \"team\" join \"TEAM_INTERACT_TYPE\" on \"type\" = \"type_id\" join \"USER\" on \"user\" = \"user_id\" join \"ROLE_ASSIGMENT\" on \"receiver\" = \"user_id\" join \"ROLE\" on \"role\" = \"role_id\" where \"role_name\" = 'Expert' and (\"type_name\" = 'Exit' or \"type_name\" = 'Expert_ejected') and \"USER\".\"user_id\" = ? group by \"user_id\", \"team_id\", \"team_name\") t2 on \"team_id_2\" = t1.\"team_id\" where case when \"count_exit\" is null then \"count_join\" else \"count_join\" - \"count_exit\" end > 0";
	private final static String SQL_SELECT_N_TEAMS_BEST_COOPERATED_EXPERT = "select \"TEAM\".*, count(\"MESSAGE\".\"author\") as counts from \"TEAM\" join \"MESSAGE_ATTACHING\" on \"team_id\" = \"team\" join \"MESSAGE\" on \"message_id\" = \"message\" join \"USER\" on \"user_id\" = \"author\" join \"ROLE_ASSIGMENT\" on \"user_id\" = \"receiver\" join \"ROLE\" on \"role_id\" = \"role\" where \"ROLE\".\"role_name\" = 'Expert' and \"USER\".\"user_id\" = ? and rownum <= ? group by \"TEAM\".\"team_id\", \"TEAM\".\"team_name\" order by counts desc";
	private final static String SQL_SELECT_TEAMS_EJECTED_EXPERT = "select distinct \"TEAM\".* from \"TEAM\" join \"TEAM_INTERACT\" on \"team_id\" = \"team\" join \"TEAM_INTERACT_TYPE\" on \"type\" = \"type_id\" join \"USER\" on \"user_id\" = \"user\" join \"ROLE_ASSIGMENT\" on \"user\" = \"receiver\" join \"ROLE\" on \"role_id\" = \"role\" where \"TEAM_INTERACT_TYPE\".\"type_name\" = 'Expert_ejected' and \"ROLE\".\"role_name\" = 'Expert' and \"USER\".\"user_id\" = ?";
	private static final String SQL_SELECT_COUNT_TEAM_MEMBERS = "select case when \"count_exit\" is null then \"count_join\" else \"count_join\" - \"count_exit\" end members from (select \"team_id\", \"team_name\", count(distinct \"TEAM_INTERACT\".\"time\") \"count_join\" from \"TEAM\" join \"TEAM_INTERACT\" on \"team_id\" = \"team\" join \"TEAM_INTERACT_TYPE\" on \"type\" = \"type_id\" join \"USER\" on \"user\" = \"user_id\" join \"ROLE_ASSIGMENT\" on \"receiver\" = \"user_id\" join \"ROLE\" on \"role\" = \"role_id\" where \"role_name\" = 'Common user' and \"type_name\" = 'Join' group by \"team_id\", \"team_name\") t1 left join (select \"team_id\" \"team_id_2\", \"team_name\" \"team_name_2\", count(distinct \"TEAM_INTERACT\".\"time\") \"count_exit\" from \"TEAM\" join \"TEAM_INTERACT\" on \"team_id\" = \"team\" join \"TEAM_INTERACT_TYPE\" on \"type\" = \"type_id\" join \"USER\" on \"user\" = \"user_id\" join \"ROLE_ASSIGMENT\" on \"receiver\" = \"user_id\" join \"ROLE\" on \"role\" = \"role_id\" where \"role_name\" = 'Common user' and \"type_name\" = 'Exit' group by \"team_id\", \"team_name\") t2 on \"team_id_2\" = t1.\"team_id\" where \"team_id\" = ?";
	private static final String SQL_TEAM_CREATE = "INSERT INTO \"TEAM\" (\"team_name\") VALUES (?)";
	private static final String SQL_TEAM_DELETE = "delete from \"TEAM\" where \"team_id\" = ?";
	
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
