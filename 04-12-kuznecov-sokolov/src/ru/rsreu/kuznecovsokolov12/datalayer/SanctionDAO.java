package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Sanction;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public interface SanctionDAO {

	List<Sanction> getUserSanctions(User receiver) throws SQLException;
	
	List<Sanction> getSanctionsByUser(User sender) throws SQLException;
	
	Sanction getLastUserSanction(User user) throws SQLException;
	
	void addSanction(Sanction sanction) throws SQLException;
}
