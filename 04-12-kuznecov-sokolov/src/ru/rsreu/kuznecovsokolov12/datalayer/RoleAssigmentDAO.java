package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.RoleAssigment;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

/**
 * The interface provides methods for assigning roles to users
 * and retrieving information about role assignments.
 *
 * <p>This interface includes methods for getting role assignments for a specific user,
 * retrieving all role assignments, and adding new role assignments.
 */
public interface RoleAssigmentDAO {

    /**
     * Retrieves role assignments for a specific user.
     *
     * @param user The user for whom to retrieve role assignments.
     * @return A list of role assignments for the specified user.
     * @throws SQLException If a database error occurs.
     */
	List<RoleAssigment> getRoleAssigmentsForUser(User user) throws SQLException;

    /**
     * Retrieves all role assignments.
     *
     * @return A list of all role assignments.
     * @throws SQLException If a database error occurs.
     */
	List<RoleAssigment> getAllRoleAssigments() throws SQLException;
	
    /**
     * Adds a new role assignment.
     *
     * @param roleAssignment The role assignment to be added.
     * @throws SQLException If a database error occurs.
     */
	void addRoleAssigment(RoleAssigment roleAssigment) throws SQLException;
}
