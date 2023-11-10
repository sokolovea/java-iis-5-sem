package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.TeamInteractDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteract;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class OracleTeamInteractDAO implements TeamInteractDAO {

	
	
	private Connection connection;

	public OracleTeamInteractDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<TeamInteract> getTeamInteractsForUser(User user) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeamInteract> getAllTeamInteracts() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
