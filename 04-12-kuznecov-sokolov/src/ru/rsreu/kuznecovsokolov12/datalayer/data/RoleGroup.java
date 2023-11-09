package ru.rsreu.kuznecovsokolov12.datalayer.data;

public class RoleGroup {
	
	private int id;
	private String name;
	
	public RoleGroup() {
	}
	
	public RoleGroup(int id, String name) {
       this.name = name;
       this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
