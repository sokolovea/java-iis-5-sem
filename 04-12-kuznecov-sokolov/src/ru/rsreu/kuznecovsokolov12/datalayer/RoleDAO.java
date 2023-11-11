package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Role;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public interface RoleDAO {
	Role getUserRole(User user) throws SQLException;
	
	List<Role> getAllRoles() throws SQLException;
}
