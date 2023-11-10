package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;
import ru.rsreu.kuznecovsokolov12.datalayer.data.*;

public interface SettingDAO {
	
	List<Setting> getSetting() throws SQLException;

	void setSetting(Setting setting) throws SQLException;
}
