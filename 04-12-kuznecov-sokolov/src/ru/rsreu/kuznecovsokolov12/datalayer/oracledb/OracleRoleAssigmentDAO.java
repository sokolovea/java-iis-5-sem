package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.RoleAssigmentDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.RoleAssigment;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class OracleRoleAssigmentDAO implements RoleAssigmentDAO {

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

}
