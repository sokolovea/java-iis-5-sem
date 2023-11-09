package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.RoleAssigment;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;


public abstract class RoleAssigmentDAO {

	public abstract List<RoleAssigment> getRoleAssigmentsForUser(User user) throws SQLException;

	public abstract List<RoleAssigment> getAllRoleAssigments() throws SQLException;
}
