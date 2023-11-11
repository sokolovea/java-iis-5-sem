package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.DeletedMessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.DeletedMessage;
import ru.rsreu.kuznecovsokolov12.datalayer.data.RoleAssigment;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;

public class OracleDeletedMessageDAO implements DeletedMessageDAO {

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
	public List<DeletedMessage> getAllDeletedMessages() {
		return null;
	}

	@Override
	public List<DeletedMessage> getDeletedMessagesForTeam(Team team) {
		// TODO Auto-generated method stub
		return null;
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
