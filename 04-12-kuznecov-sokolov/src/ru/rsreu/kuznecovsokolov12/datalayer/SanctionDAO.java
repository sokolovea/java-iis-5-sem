package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Sanction;
import ru.rsreu.kuznecovsokolov12.datalayer.data.SanctionType;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

/**
 * The interface provides methods for working with sanctions
 * (blocking and unblocking) imposed on users.
 *
 * <p>This interface includes methods for retrieving sanctions received or sent by a specific user,
 * getting the last sanction imposed on a user, retrieving a sanction type by its name, and adding new sanctions.
 */
public interface SanctionDAO {

    /**
     * Retrieves sanctions received by a specific user.
     *
     * @param receiver The user for whom to retrieve sanctions.
     * @return A list of sanctions received by the specified user.
     * @throws SQLException If a database error occurs.
     */
	List<Sanction> getUserSanctions(User receiver) throws SQLException;
	
    /**
     * Retrieves sanctions sent by a specific user.
     *
     * @param sender The user for whom to retrieve sanctions.
     * @return A list of sanctions sent by the specified user.
     * @throws SQLException If a database error occurs.
     */
	List<Sanction> getSanctionsByUser(User sender) throws SQLException;
	
    /**
     * Retrieves the last sanction imposed on a specific user.
     *
     * @param user The user for whom to retrieve the last sanction.
     * @return The last sanction imposed on the specified user.
     * @throws SQLException If a database error occurs.
     */
	Sanction getLastUserSanction(User user) throws SQLException;
	

    /**
     * Retrieves a sanction type by its name.
     *
     * @param name The name of the sanction type to retrieve.
     * @return The sanction type with the specified name.
     * @throws SQLException If a database error occurs.
     */
	SanctionType getSanctionTypeByName(String name) throws SQLException;
	
    /**
     * Adds a new sanction.
     *
     * @param sanction The sanction to be added.
     * @throws SQLException If a database error occurs.
     */
	void addSanction(Sanction sanction) throws SQLException;
}
