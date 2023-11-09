package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;
import ru.rsreu.kuznecovsokolov12.datalayer.data.*;

public abstract class SettingDAO {
	
	public abstract List<Setting> getSetting() throws SQLException;

	public abstract void setSetting(Setting setting) throws SQLException;
}
