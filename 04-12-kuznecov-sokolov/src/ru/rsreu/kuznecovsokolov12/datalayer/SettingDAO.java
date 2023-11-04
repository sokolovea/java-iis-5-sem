package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

public abstract class SettingDAO {
	
	public abstract List<String> getNetworkInformationSystemSettings() throws SQLException;

	public abstract void setNetworkInformationSystemSettings(String settings) throws SQLException;
}
