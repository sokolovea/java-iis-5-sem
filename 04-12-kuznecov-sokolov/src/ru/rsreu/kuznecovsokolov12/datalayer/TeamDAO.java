package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;

public interface TeamDAO {
	
	List<Team> getAllTeam() throws SQLException;

	Team getTeamByName(String name) throws SQLException;
	
	Team getTeamById(int id) throws SQLException;
}
