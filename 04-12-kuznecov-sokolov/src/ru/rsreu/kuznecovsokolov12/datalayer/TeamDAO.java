package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

public abstract class TeamDAO {
	
	public abstract List<String> getCommonTeamInfo() throws SQLException;
}
