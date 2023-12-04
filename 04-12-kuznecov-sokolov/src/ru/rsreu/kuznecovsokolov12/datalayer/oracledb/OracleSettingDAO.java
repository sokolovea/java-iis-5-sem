package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Setting;
import ru.rsreu.kuznecovsokolov12.servlet.ResourcerHolder;

public class OracleSettingDAO implements SettingDAO, ResourcerHolder {

	private final static String SQL_SETTING_UPDATE = resourser.getString("sql.setting.update");
	private final static String SQL_SETTING_SELECT = resourser.getString("sql.setting.select_all");

	public final static String COLUMN_SETTING_ID 		= resourser.getString("sql.setting.column.id");
	public final static String COLUMN_SETTING_NAME 		= resourser.getString("sql.setting.column.name");
	public final static String COLUMN_SETTING_VALUUE 	= resourser.getString("sql.setting.column.value");
	
	private Connection connection;
	
	public OracleSettingDAO(Connection connection) {
		this.connection = connection;
	}
	
	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<Setting> getSetting() throws SQLException {
		List<Setting> settingList = new ArrayList<Setting>();
		Statement statement = this.getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery(SQL_SETTING_SELECT);
		while (resultSet.next()) {
			Setting setting = new Setting();
			setting.setId(resultSet.getInt(COLUMN_SETTING_ID));
			setting.setName(resultSet.getString(COLUMN_SETTING_NAME));
			setting.setValue(resultSet.getInt(COLUMN_SETTING_VALUUE));
			settingList.add(setting);
		}
		return settingList;
	}

	@Override
	public void setSetting(Setting setting) throws SQLException {
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_SETTING_UPDATE);
		ps.setInt(1, setting.getValue());
		ps.setString(2, setting.getName());
		ps.executeUpdate();
	}
}
