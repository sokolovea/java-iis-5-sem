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

import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Setting;

public class OracleSettingDAO implements SettingDAO {

	private final static String SQL_SETTING_UPDATE = "update \"SETTING\" set \"value\" = ? where \"SETTING\".\"name\" = ?";
	private final static String SQL_SETTING_SELECT = "select * FROM \"SETTING\"";

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
			setting.setId(resultSet.getInt("ID"));
			setting.setName(resultSet.getString("NAME"));
			setting.setValue(resultSet.getInt("VALUE"));
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
