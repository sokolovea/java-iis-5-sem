package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.TableRow;

public abstract class QueryDAO {
	
	public abstract List<TableRow> getTeachersByGroupNumber(String groupNumber) throws SQLException;
	
	public abstract List<TableRow> getTeachersAndWorkload() throws SQLException;
	
	public abstract List<TableRow> getWorkloadByDate(String beginDate, String endDate) throws SQLException;
}
