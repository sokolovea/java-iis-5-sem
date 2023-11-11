package ru.rsreu.kuznecovsokolov12.datalayer;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.*;

public interface DeletedMessageDAO {
	
	List<DeletedMessage> getAllDeletedMessages();
	
	List<DeletedMessage> getDeletedMessagesForTeam(Team team);

}
