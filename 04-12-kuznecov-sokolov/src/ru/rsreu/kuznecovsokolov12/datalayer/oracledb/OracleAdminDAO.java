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

import ru.rsreu.kuznecovsokolov12.datalayer.AdminDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TableRow;

public class OracleAdminDAO extends AdminDAO {

	private Connection connection;

	public OracleAdminDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<String> getNetworkInformationSystemSettings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNetworkInformationSystemSettings(String settings) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getUserInfo() throws SQLException {
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
