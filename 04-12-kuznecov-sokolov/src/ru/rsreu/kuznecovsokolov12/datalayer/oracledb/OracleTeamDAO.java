package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;

public class OracleTeamDAO implements TeamDAO {

	private Connection connection;

	public OracleTeamDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<Team> getAllTeam() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team getTeamByName(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team getTeamById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
