package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.MessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class OracleMessageDAO implements MessageDAO {

	private final static String SQL_USER_SELECT_BY_ID = "SELECT * FROM \"USER\" WHERE \"id\" = ?";
	private final static String SQL_USER_SELECT_BY_LOGIN = "SELECT * FROM \"USER\", \"MESSAGE\" WHERE \"login\" = ?";
	private final static String SQL_ALL_USERS_SELECT = "select * FROM \"SETTING\"";
	private final static String SQL_USER_UPDATE = "update \"USER\" set \"login\" = ?, \"password\" = ?, \"is_authorized\" = ?, \"name\" = ?, \"email\" = ? where \"USER\".\"id\" = ?";
	private final static String SQL_USER_CREATE = "INSERT INTO \"USER\" (\"login\", \"password\", \"name\", \"email\") VALUES (?, ?, ?, ?)";
	
	private final static String COLUMN_MESSAGE_ID 		  = "ID";
	private final static String COLUMN_MESSAGE_DATA 	  = "DATA";
	private final static String COLUMN_MESSAGE_TIME 	  = "TIME";
	private final static String[] ALL_MESSAGE_COLUMNS = {COLUMN_MESSAGE_ID, COLUMN_MESSAGE_DATA, COLUMN_MESSAGE_TIME};
	
	
	private Connection connection;

	public OracleMessageDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<Message> getUndeletedMessagesForTeam(Team team) throws SQLException {
		PreparedStatement ps;
		List<Message> result = new ArrayList<Message>();
		ps = this.connection.prepareStatement(SQL_ALL_USERS_SELECT);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Message message = getMessageData(resultSet, ALL_MESSAGE_COLUMNS);
			result.add(message);
		}
		return result;
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
	
	public static Message getMessageData(ResultSet resultSet, String... columns) throws SQLException {
		Message message = new Message();
		for (String column : columns) {
			if (column.equals(COLUMN_MESSAGE_ID)) {
				message.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_MESSAGE_DATA)) {
				message.setData(resultSet.getString(column));
				continue;
			}
			
			if (column.equals(COLUMN_MESSAGE_TIME)) {
				message.setTime(resultSet.getTimestamp(column));
				continue;
			}
		}
		return message;
	}
	
	public static Message getMessageData(ResultSet resultSet, int... columns) throws SQLException {
		Message message = new Message();
		for (int i = 0; i < columns.length; i++) {
			if (i == 0) {
				message.setId(resultSet.getInt(columns[i]));
				continue;
			}
			
			if (i == 1) {
				message.setData(resultSet.getString(columns[i]));
				continue;
			}
			
			if (i == 2) {
				message.setTime(resultSet.getTimestamp(columns[i]));
				continue;
			}
		}
		return message;
	}

}
