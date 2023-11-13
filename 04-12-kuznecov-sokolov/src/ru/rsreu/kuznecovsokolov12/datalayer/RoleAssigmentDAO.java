package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.RoleAssigment;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;


public interface RoleAssigmentDAO {

	List<RoleAssigment> getRoleAssigmentsForUser(User user) throws SQLException;

	List<RoleAssigment> getAllRoleAssigments() throws SQLException;
	
	void addRoleAssigment(RoleAssigment roleAssigment) throws SQLException;
}
