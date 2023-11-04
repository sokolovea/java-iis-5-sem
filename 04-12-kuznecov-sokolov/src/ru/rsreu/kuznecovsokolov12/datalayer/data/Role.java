package ru.rsreu.kuznecovsokolov12.datalayer.data;

public class Role extends AbstractEntity {

	private int id;
	private String name;
	private RoleGroup group;
	
	public Role() {
	}

	public Role(int id, String name, RoleGroup group) {
		this.setId(id);
		this.setName(name);
		this.setGroup(group);
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

	public RoleGroup getGroup() {
		return group;
	}

	public void setGroup(RoleGroup group) {
		this.group = group;
	}
	
	

}
