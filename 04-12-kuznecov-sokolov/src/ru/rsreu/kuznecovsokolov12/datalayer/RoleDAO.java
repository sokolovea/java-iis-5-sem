package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Role;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

/**
 * The interface provides methods for working with user roles.
 *
 * <p>This interface includes methods for retrieving the role of a specific user,
 * getting a role by its name, and retrieving a list of all roles.
 */
public interface RoleDAO {
	
    /**
     * Retrieves the role of a specific user.
     *
     * @param user The user for whom to retrieve the role.
     * @return The role of the specified user.
     * @throws SQLException If a database error occurs.
     */
	Role getUserRole(User user) throws SQLException;
	
    /**
     * Retrieves a role by its name.
     *
     * @param name The name of the role to retrieve.
     * @return The role with the specified name.
     * @throws SQLException If a database error occurs.
     */
	Role getRoleByName(String name) throws SQLException;
	
    /**
     * Retrieves a list of all roles.
     *
     * @return A list of all roles.
     * @throws SQLException If a database error occurs.
     */
	List<Role> getAllRoles() throws SQLException;
}
