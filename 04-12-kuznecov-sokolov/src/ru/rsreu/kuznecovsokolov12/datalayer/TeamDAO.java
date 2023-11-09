package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;

public abstract class TeamDAO {
	
	public abstract List<Team> getAllTeam() throws SQLException;

	public abstract Team getTeamByName(String name) throws SQLException;
	
	public abstract Team getTeamById(int id) throws SQLException;
}
