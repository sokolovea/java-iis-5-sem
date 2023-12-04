package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ru.rsreu.kuznecovsokolov12.datalayer.DeletedMessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.DeletedMessage;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Role;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;
import ru.rsreu.kuznecovsokolov12.servlet.ResourcerHolder;

public class OracleDeletedMessageDAO implements DeletedMessageDAO, ResourcerHolder {

	private static final String SQL_SELECT_DELETED_MESSAGE_FOR_MESSAGE = resourser.getString("sql.del_message.select_all_role_assigment");
	private static final String SQL_DELETED_MESSAGE_DELETE = resourser.getString("sql.del_message.delete");
	private static final String SQL_DELETED_MESSAGE_CREATE = resourser.getString("sql.del_message.create");

	public final static String COLUMN_DEL_MESSAGE_ID 	  = resourser.getString("sql.del_message.column.id");
	public final static String COLUMN_DEL_MESSAGE_TIME 	  = resourser.getString("sql.del_message.column.time");
	public final static String[] ALL_DEL_MESSAGE_COLUMNS = {COLUMN_DEL_MESSAGE_ID, COLUMN_DEL_MESSAGE_TIME};

	
	private Connection connection;
	
	public OracleDeletedMessageDAO(Connection connection) {
		this.connection = connection;
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	@Override
	public void addDeletedMessage(DeletedMessage deletedMessage) throws SQLException {
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_DELETED_MESSAGE_CREATE);
		ps.setInt(1, deletedMessage.getSender().getId());
		ps.setInt(2, deletedMessage.getMessage().getId());
		ps.executeUpdate();
	}
	
	@Override
	public void removeFromDeletedMessage(DeletedMessage deletedMessage) throws SQLException {
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_DELETED_MESSAGE_DELETE);
		ps.setInt(1, deletedMessage.getMessage().getId());
		ps.executeUpdate();
	}
	
	@Override
	public DeletedMessage getDeletedMessage(Message message) throws SQLException {
		DeletedMessage delMessage = new DeletedMessage();
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_SELECT_DELETED_MESSAGE_FOR_MESSAGE);
		ps.setInt(1, message.getId());
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			delMessage = getDelMessageData(resultSet, ALL_DEL_MESSAGE_COLUMNS);
			delMessage.setSender(OracleUserDAO.getUserData(resultSet, OracleUserDAO.COLUMN_USER_ID));
			message = OracleMessageDAO.getMessageData(resultSet, OracleMessageDAO.COLUMN_MESSAGE_ID);
			message.setAuthor(new User(resultSet.getInt(5)));
			delMessage.setMessage(message);
		}
		return delMessage;
	}
	
	public static DeletedMessage getDelMessageData(ResultSet resultSet, String... columns) throws SQLException {
		DeletedMessage delMessage = new DeletedMessage();
		for (String column : columns) {
			if (column.equals(COLUMN_DEL_MESSAGE_ID)) {
				delMessage.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_DEL_MESSAGE_TIME)) {
				delMessage.setTime(resultSet.getTimestamp(column));
				continue;
			}
		}
		return delMessage ;
	}
	
	
}