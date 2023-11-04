package ru.rsreu.kuznecovsokolov12.datalayer.data;

public class TableRow {

	private int teacherCode = 0;
	private String teacherFirstName = "";
	private String teacherLastName = "";
	private String teacherPatronymic = "";
	private int teacherWorkExperienceMonths = 0;
	
	private String groupNumber = "";
	private String groupSpeciality = "";
	private String groupFaculcy = "";
	private int studentsCount = 0;
	
	private int workloadHoursCount = 0;
	private String workloadDiscipline = "";
	private String workloadDisciplineType = "";
	private String workloadDate = "";
	
	public TableRow() {
		
	}

	public int getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(int teacherCode) {
		this.teacherCode = teacherCode;
	}

	public String getTeacherFirstName() {
		return teacherFirstName;
	}

	public void setTeacherFirstName(String teacherFirstName) {
		this.teacherFirstName = teacherFirstName;
	}

	public String getTeacherLastName() {
		return teacherLastName;
	}

	public void setTeacherLastName(String teacherLastName) {
		this.teacherLastName = teacherLastName;
	}

	public String getTeacherPatronymic() {
		return teacherPatronymic;
	}

	public void setTeacherPatronymic(String teacherPatronymic) {
		this.teacherPatronymic = teacherPatronymic;
	}

	public int getTeacherWorkExperienceMonths() {
		return teacherWorkExperienceMonths;
	}

	public void setTeacherWorkExperienceMonths(int teacherWorkExperienceMonths) {
		this.teacherWorkExperienceMonths = teacherWorkExperienceMonths;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public String getGroupSpeciality() {
		return groupSpeciality;
	}

	public void setGroupSpeciality(String groupSpeciality) {
		this.groupSpeciality = groupSpeciality;
	}

	public String getGroupFaculcy() {
		return groupFaculcy;
	}

	public void setGroupFaculcy(String groupFaculcy) {
		this.groupFaculcy = groupFaculcy;
	}

	public int getStudentsCount() {
		return studentsCount;
	}

	public void setStudentsCount(int studentsCount) {
		this.studentsCount = studentsCount;
	}

	public int getWorkloadHoursCount() {
		return workloadHoursCount;
	}

	public void setWorkloadHoursCount(int workloadHoursCount) {
		this.workloadHoursCount = workloadHoursCount;
	}

	public String getWorkloadDiscipline() {
		return workloadDiscipline;
	}

	public void setWorkloadDiscipline(String workloadDiscipline) {
		this.workloadDiscipline = workloadDiscipline;
	}

	public String getWorkloadDisciplineType() {
		return workloadDisciplineType;
	}

	public void setWorkloadDisciplineType(String workloadDisciplineType) {
		this.workloadDisciplineType = workloadDisciplineType;
	}

	public String getWorkloadDate() {
		return this.workloadDate;
	}
	
	public void setWorkloadDate(String workloadDate) {
		this.workloadDate = workloadDate;
	}
	
}
