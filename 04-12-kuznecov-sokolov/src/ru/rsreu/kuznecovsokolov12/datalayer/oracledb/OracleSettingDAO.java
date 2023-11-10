package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
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
		Resourcer resourcer = ProjectResourcer.getInstance();
		ResultSet resultSet = statement
				.executeQuery("select * FROM \"SETTING\"");
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
		Statement statement = this.getConnection().createStatement();
		statement.executeUpdate("update \"SETTING\" set \"value\" = " + setting.getValue() + " where \"SETTING\".\"name\" = " + setting.getName());
	}
}
