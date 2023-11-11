package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.SanctionDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Sanction;
import ru.rsreu.kuznecovsokolov12.datalayer.data.SanctionType;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteract;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteractType;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class OracleSanctionDAO implements SanctionDAO {
	
	private final static String COLUMN_SANCTION_ID 		= "sanction_id";
	private final static String COLUMN_SANCTION_REASON 	= "reason";
	private final static String COLUMN_SANCTION_TIME 	= "time";
	private final static String[] ALL_SANCTION_COLUMNS = {COLUMN_SANCTION_ID, COLUMN_SANCTION_REASON, COLUMN_SANCTION_TIME};
	
	private final static String COLUMN_SANCTION_TYPE_ID 	= "sanction_t_id";
	private final static String COLUMN_SANCTION_TYPE_NAME 	= "sanction_t_name";
	private final static String[] ALL_SANCTION_TYPE_COLUMNS = {COLUMN_SANCTION_TYPE_ID, COLUMN_SANCTION_TYPE_NAME};
	
	
	private Connection connection;

	public OracleSanctionDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<Sanction> getUserSanctions(User receiver) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sanction> getSanctionsByUser(User sender) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sanction getLastUserSanction(User sender) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Sanction getSanctionData(ResultSet resultSet, String... columns) throws SQLException {
		Sanction sanction = new Sanction();
		for (String column : columns) {
			if (column.equals(COLUMN_SANCTION_ID)) {
				sanction.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_SANCTION_REASON)) {
				sanction.setReason(resultSet.getString(column));
				continue;
			}
			
			if (column.equals(COLUMN_SANCTION_TIME)) {
				sanction.setTime(resultSet.getTimestamp(column));
				continue;
			}
		}
		return sanction ;
	}
	
	public static SanctionType getSanctionTypeData(ResultSet resultSet, String... columns) throws SQLException {
		SanctionType sanctionType = new SanctionType();
		for (String column : columns) {
			if (column.equals(COLUMN_SANCTION_TYPE_ID)) {
				sanctionType.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_SANCTION_TYPE_NAME)) {
				sanctionType.setName(resultSet.getString(column));
				continue;
			}
		}
		return sanctionType;
	}

}
