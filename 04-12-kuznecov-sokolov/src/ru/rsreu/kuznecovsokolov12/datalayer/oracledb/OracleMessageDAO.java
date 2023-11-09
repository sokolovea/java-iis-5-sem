package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.MessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;

public class OracleMessageDAO extends MessageDAO {

	private Connection connection;

	public OracleMessageDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<Message> getUndeletedMessagesForTeam(Team team) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getAllMessagesForTeam(Team team) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getAllMessages() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
