package ru.rsreu.kuznecovsokolov12.datalayer;
import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.*;

public interface DeletedMessageDAO {

	void addDeletedMessage(DeletedMessage deletedMessage) throws SQLException;
	
}
