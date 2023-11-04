package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import ru.rsreu.kuznecovsokolov12.datalayer.QueryDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TableRow;

public class OracleQueryDAO extends QueryDAO {
	public static final int DATE_STRING_LENGTH = 10;
	public static final String CODE = "CODE";
	public static final String FIRST_NAME = "FIRST_NAME";
	public static final String PATRONYMIC_NAME = "PATRONYMIC_NAME";
	public static final String SECOND_NAME = "SECOND_NAME";
	public static final String WORK_EXPERIENCE_MONTHS = "WORK_EXPERIENCE_MONTHS";
	public static final String SUM_HOURS = "SUM_HOURS";
	public static final String GROUP_NUMBER = "GROUP_NUMBER";
	public static final String FACULCY = "FACULCY";
	public static final String DISCIPLINE = "DISCIPLINE";
	public static final String DISCIPLINE_TYPE = "DISCIPLINE_TYPE";
	public static final String LESSON_DATE = "LESSON_DATE";
	public static final String HOURS_COUNT = "HOURS_COUNT";

	private Connection connection;

	public OracleQueryDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<TableRow> getTeachersByGroupNumber(String groupNumber) throws SQLException {
		List<TableRow> tableRowList = new ArrayList<TableRow>();
		Statement statement = this.getConnection().createStatement();
		Resourcer resourcer = ProjectResourcer.getInstance();
		ResultSet resultSet = statement
				.executeQuery(resourcer.getString("SQL.SELECT_TEACHERS_BY_GROUP") + "'" + groupNumber + "'");
		while (resultSet.next()) {
			TableRow tableRow = new TableRow();
			tableRow.setTeacherCode(resultSet.getInt(OracleQueryDAO.CODE));
			tableRow.setTeacherFirstName(resultSet.getString(OracleQueryDAO.FIRST_NAME));
			tableRow.setTeacherPatronymic(resultSet.getString(OracleQueryDAO.PATRONYMIC_NAME));
			tableRow.setTeacherLastName(resultSet.getString(OracleQueryDAO.SECOND_NAME));
			tableRow.setTeacherWorkExperienceMonths(resultSet.getInt(OracleQueryDAO.WORK_EXPERIENCE_MONTHS));
			tableRowList.add(tableRow);
		}
		return tableRowList;
	}

	public List<TableRow> getTeachersAndWorkload() throws SQLException {
		List<TableRow> tableRowList = new ArrayList<TableRow>();
		Statement statement = this.getConnection().createStatement();
		Resourcer resourcer = ProjectResourcer.getInstance();
		ResultSet resultSet = statement.executeQuery(resourcer.getString("SQL.SELECT_TEACHERS_AND_WORKLOAD"));
		while (resultSet.next()) {
			TableRow tableRow = new TableRow();
			tableRow.setTeacherCode(resultSet.getInt(OracleQueryDAO.CODE));
			tableRow.setTeacherFirstName(resultSet.getString(OracleQueryDAO.FIRST_NAME));
			tableRow.setTeacherPatronymic(resultSet.getString(OracleQueryDAO.PATRONYMIC_NAME));
			tableRow.setTeacherLastName(resultSet.getString(OracleQueryDAO.SECOND_NAME));
			tableRow.setWorkloadHoursCount(resultSet.getInt(OracleQueryDAO.SUM_HOURS));
			tableRowList.add(tableRow);
		}
		return tableRowList;
	}

	public List<TableRow> getWorkloadByDate(String beginDate, String endDate) throws SQLException {
		List<TableRow> tableRowList = new ArrayList<TableRow>();
		Statement statement = this.getConnection().createStatement();
		Resourcer resourcer = ProjectResourcer.getInstance();
		String queryString = resourcer.getString("SQL.WORKLOAD_BY_DATE");
		ResultSet resultSet = statement
				.executeQuery(queryString.replace("beginDate", beginDate).replace("endDate", endDate));
		while (resultSet.next()) {
			TableRow tableRow = new TableRow();
			tableRow.setTeacherFirstName(resultSet.getString(OracleQueryDAO.FIRST_NAME));
			tableRow.setTeacherPatronymic(resultSet.getString(OracleQueryDAO.PATRONYMIC_NAME));
			tableRow.setTeacherLastName(resultSet.getString(OracleQueryDAO.SECOND_NAME));
			tableRow.setGroupNumber(resultSet.getString(OracleQueryDAO.GROUP_NUMBER));
			tableRow.setGroupFaculcy(resultSet.getString(OracleQueryDAO.FACULCY));
			tableRow.setWorkloadDiscipline(resultSet.getString(OracleQueryDAO.DISCIPLINE));
			tableRow.setWorkloadDisciplineType(resultSet.getString(OracleQueryDAO.DISCIPLINE_TYPE));
			tableRow.setWorkloadDate(resultSet.getString(OracleQueryDAO.LESSON_DATE).substring(0, OracleQueryDAO.DATE_STRING_LENGTH));
			tableRow.setWorkloadHoursCount(resultSet.getInt(OracleQueryDAO.HOURS_COUNT));
			tableRowList.add(tableRow);
		}
		return tableRowList;
	}

}
