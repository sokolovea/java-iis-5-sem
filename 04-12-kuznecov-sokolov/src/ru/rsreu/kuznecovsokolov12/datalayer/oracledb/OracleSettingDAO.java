package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Setting;

public class OracleSettingDAO extends SettingDAO {

	private Connection connection;

	public OracleSettingDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Setting getSetting() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSetting(Setting setting) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
