package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.RoleAssigmentDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.RoleAssigment;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class OracleRoleAssigmentDAO implements RoleAssigmentDAO {

	private final static String COLUMN_ROLE_ASSIGMENT_ID 	= "role_assigment_id";
	private final static String COLUMN_ROLE_ASSIGMENT_TIME 	= "time";
	private final static String[] ALL_ROLE_ASSIGMENT_COLUMNS = {COLUMN_ROLE_ASSIGMENT_ID, COLUMN_ROLE_ASSIGMENT_TIME};
	
	private Connection connection;

	public OracleRoleAssigmentDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<RoleAssigment> getRoleAssigmentsForUser(User user) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleAssigment> getAllRoleAssigments() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static RoleAssigment getRoleAssigmentData(ResultSet resultSet, String... columns) throws SQLException {
		RoleAssigment roleAssigment = new RoleAssigment();
		for (String column : columns) {
			if (column.equals(COLUMN_ROLE_ASSIGMENT_ID)) {
				roleAssigment.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_ROLE_ASSIGMENT_TIME)) {
				roleAssigment.setTime(resultSet.getTimestamp(column));
				continue;
			}
		}
		return roleAssigment ;
	}

}
