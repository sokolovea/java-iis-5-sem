package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Role;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public abstract class RoleDAO {
	public abstract Role getUserRole(User user) throws SQLException;
}
