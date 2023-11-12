package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.rsreu.kuznecovsokolov12.datalayer.MessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class OracleMessageDAO implements MessageDAO {

	private final static String SQL_ALL_MESSAGES_SELECT = "select * FROM \"MESSAGE\" join \"USER\" on \"user_id\" = \"author\"";
	private final static String SQL_SELECT_UNDELETED_MESSAGES_FOR_TEAM = "select \"MESSAGE\".*, \"USER\".* FROM \"MESSAGE\" join \"USER\" on \"user_id\" = \"author\" join \"MESSAGE_ATTACHING\" on \"message_id\" = \"message\" left join \"DELETED_MESSAGE\" on \"message_id\" = \"DELETED_MESSAGE\".\"message\" where \"team\" = ? and \"del_message_id\" is null";
	private final static String SQL_SELECT_DELETED_MESSAGES_FOR_TEAM = "select \"MESSAGE\".*, \"USER\".* FROM \"MESSAGE\" join \"USER\" on \"user_id\" = \"author\" join \"MESSAGE_ATTACHING\" on \"message_id\" = \"message\" join \"DELETED_MESSAGE\" on \"message_id\" = \"DELETED_MESSAGE\".\"message\" where \"team\" = ?";
	private final static String SQL_SELECT_ALL_MESSAGES_FOR_TEAM = "select \"MESSAGE\".*, \"USER\".* FROM \"MESSAGE\" join \"USER\" on \"user_id\" = \"author\" join \"MESSAGE_ATTACHING\" on \"message_id\" = \"message\" where \"team\" = ?";
	private final static String SQL_SELECT_MESSAGES_DEL_BY_NO_SELF_USER = "select \"MESSAGE\".* from \"USER\" join \"MESSAGE\" on \"USER\".\"user_id\" = \"MESSAGE\".\"author\" join \"DELETED_MESSAGE\" on \"MESSAGE\".\"message_id\" = \"DELETED_MESSAGE\".\"message\" where \"DELETED_MESSAGE\".\"sender\" != \"USER\".\"user_id\" and \"USER\".\"user_id\" = ?";
	private final static String SQL_SELECT_COUNT_MESSAGES_BY_USER = "select count(\"MESSAGE\".\"message_id\") as \"count_messages\" from \"USER\" join \"MESSAGE\" on \"USER\".\"user_id\" = \"MESSAGE\".\"author\" where \"USER\".\"user_id\" = ?";
	private final static String SQL_SELECT_COUNT_DEL_MESSAGES_BY_USER = "select count(\"MESSAGE\".\"message_id\") as \"count_del_messages\" from \"USER\" join \"MESSAGE\" on \"USER\".\"user_id\" = \"MESSAGE\".\"author\" join \"DELETED_MESSAGE\" on \"message\" = \"message_id\" where \"USER\".\"user_id\" = ?";
	
	public final static String COLUMN_MESSAGE_ID 	  = "message_id";
	public final static String COLUMN_MESSAGE_DATA 	  = "data";
	public final static String COLUMN_MESSAGE_TIME 	  = "message_time";
	public final static String[] ALL_MESSAGE_COLUMNS = {COLUMN_MESSAGE_ID, COLUMN_MESSAGE_DATA, COLUMN_MESSAGE_TIME};
	
	
	private Connection connection;

	public OracleMessageDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<Message> getAllMessages() throws SQLException {
		PreparedStatement ps;
		List<Message> result = new ArrayList<Message>();
		ps = this.connection.prepareStatement(SQL_ALL_MESSAGES_SELECT);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Message message = getMessageData(resultSet, ALL_MESSAGE_COLUMNS);
			message.setAuthor(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			result.add(message);
		}
		return result;
	}
	
	@Override
	public List<Message> getUndeletedMessagesForTeam(Team team) throws SQLException {
		PreparedStatement ps;
		List<Message> result = new ArrayList<Message>();
		ps = this.connection.prepareStatement(SQL_SELECT_UNDELETED_MESSAGES_FOR_TEAM);
		ps.setInt(1, team.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Message message = getMessageData(resultSet, ALL_MESSAGE_COLUMNS);
			message.setAuthor(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			result.add(message);
		}
		return result;
	}

	@Override
	public Set<Message> getDeletedMessagesForTeam(Team team) throws SQLException {
		PreparedStatement ps;
		Set<Message> result = new HashSet<Message>();
		ps = this.connection.prepareStatement(SQL_SELECT_DELETED_MESSAGES_FOR_TEAM);
		ps.setInt(1, team.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Message message = getMessageData(resultSet, ALL_MESSAGE_COLUMNS);
			message.setAuthor(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			result.add(message);
		}
		return result;
	}
	
	@Override
	public List<Message> getAllMessagesForTeam(Team team) throws SQLException {
		PreparedStatement ps;
		List<Message> result = new ArrayList<Message>();
		ps = this.connection.prepareStatement(SQL_SELECT_ALL_MESSAGES_FOR_TEAM);
		ps.setInt(1, team.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Message message = getMessageData(resultSet, ALL_MESSAGE_COLUMNS);
			message.setAuthor(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			result.add(message);
		}
		return result;
	}

	@Override
	public List<Message> getMessagesDeletedByNoSelfUser(User user) throws SQLException {
		PreparedStatement ps;
		List<Message> result = new ArrayList<Message>();
		ps = this.connection.prepareStatement(SQL_SELECT_MESSAGES_DEL_BY_NO_SELF_USER);
		ps.setInt(1, user.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Message message = getMessageData(resultSet, ALL_MESSAGE_COLUMNS);
			result.add(message);
		}
		return result;
	}

	@Override
	public int getCountMessagesSendedByUser(User user) throws SQLException {
		PreparedStatement ps;
		int result = 0;
		ps = this.connection.prepareStatement(SQL_SELECT_COUNT_MESSAGES_BY_USER);
		ps.setInt(1, user.getId());
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			result = resultSet.getInt(1);
		}
		return result;
	}

	@Override
	public int getCountDeletedMessagesSendedByUser(User user) throws SQLException {
		PreparedStatement ps;
		int result = 0;
		ps = this.connection.prepareStatement(SQL_SELECT_COUNT_DEL_MESSAGES_BY_USER);
		ps.setInt(1, user.getId());
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			result = resultSet.getInt(1);
		}
		return result;
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


}
