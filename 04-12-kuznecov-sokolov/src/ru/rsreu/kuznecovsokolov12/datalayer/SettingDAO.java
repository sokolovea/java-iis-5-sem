package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;
import ru.rsreu.kuznecovsokolov12.datalayer.data.*;

/**
 * The interface works with the settings of a network information system.
 *
 * <p>This interface includes methods for retrieving a list of settings and updating settings.
 */
public interface SettingDAO {
	
	/**
	 * Retrieves a list of settings for the network information system.
	 *
	 * @return A list of Setting objects representing the settings.
	 * @throws SQLException If a database error occurs.
	 */
	List<Setting> getSetting() throws SQLException;

	/**
	 * Updates a setting in the network information system.
	 *
	 * @param setting The Setting object representing the setting to be updated.
	 * @throws SQLException If a database error occurs.
	 */
	void setSetting(Setting setting) throws SQLException;
}
