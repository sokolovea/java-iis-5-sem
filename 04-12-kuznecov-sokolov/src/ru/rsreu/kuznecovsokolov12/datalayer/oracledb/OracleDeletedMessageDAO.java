package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ru.rsreu.kuznecovsokolov12.datalayer.DeletedMessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.DeletedMessage;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;

public class OracleDeletedMessageDAO implements DeletedMessageDAO {

	private static final String SQL_ADD_DELETED_MESSAGE_CREATE = "INSERT INTO \"DELETED_MESSAGE\" (\"sender\", \"message\", \"del_message_time\") VALUES (?, ?, (select sysdate from dual));";

	public final static String COLUMN_DEL_MESSAGE_ID 	  = "del_message_id";
	public final static String COLUMN_DEL_MESSAGE_TIME 	  = "del_message_time";
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
		ps = this.connection.prepareStatement(SQL_ADD_DELETED_MESSAGE_CREATE);
		ps.setInt(1, deletedMessage.getSender().getId());
		ps.setInt(2, deletedMessage.getMessage().getId());
		ps.executeUpdate();
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