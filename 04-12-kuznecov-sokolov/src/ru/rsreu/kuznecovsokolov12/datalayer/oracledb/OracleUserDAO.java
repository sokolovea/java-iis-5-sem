package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TableRow;

public class OracleUserDAO extends UserDAO {

	private Connection connection;

	public OracleUserDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}
	
	@Override
	public List<String> getUsersListForAuthorization() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUsersListForAdmin() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUsersListForModerator() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUsersListForOtherUser() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUserInfoForAdmin() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUserInfoForAdminReport() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserInfo(String userInfo) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getUserRoleHistory() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
