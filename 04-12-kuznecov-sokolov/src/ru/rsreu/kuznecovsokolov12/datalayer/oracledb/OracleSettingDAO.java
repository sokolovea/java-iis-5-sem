package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;

public class OracleSettingDAO extends SettingDAO {

	private Connection connection;

	public OracleSettingDAO(Connection connection) {
		this.connection = connection;
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
	
	

}
