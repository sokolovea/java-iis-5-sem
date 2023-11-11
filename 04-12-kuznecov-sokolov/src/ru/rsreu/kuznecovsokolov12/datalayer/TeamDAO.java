package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public interface TeamDAO {
	
	Map<Team, Map<String, Integer>> getAllTeam() throws SQLException;

	Team getTeamByName(String name) throws SQLException;
	
	Team getTeamById(int id) throws SQLException;
	
	List<Team> getTeamsConsultedByExpert(User expert) throws SQLException;
	
	Map<Team, Integer> getNTeamsBestCooperatedExpert(User expert, int N) throws SQLException;
	
	List<Team> getTeamsEjectedExpert(User expert) throws SQLException;
	
}
