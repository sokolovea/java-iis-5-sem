package ru.rsreu.kuznecovsokolov12.datalayer.data;

public class Role extends AbstractEntity {

	private int id;
	private String name;
	private int group;
	
	public Role() {
	}

	public Role(int id, String name, int group) {
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

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
	
	

}
