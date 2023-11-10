package ru.rsreu.kuznecovsokolov12.datalayer.data;

public class Role extends AbstractEntity {

	private String name;
	private RoleGroup group;
	
	public Role() {
	}

	public Role(int id) {
		super(id);
	}
	
	public Role(int id, String name, RoleGroup group) {
		this(id);
		this.setName(name);
		this.setGroup(group);
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
