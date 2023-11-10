package ru.rsreu.kuznecovsokolov12.datalayer.data;

public class RoleGroup extends AbstractEntity {
	
	private String name;
	
	public RoleGroup() {
	}
	
	public RoleGroup(int id) {
		super(id);
	}
	
	public RoleGroup(int id, String name) {
		this(id);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
