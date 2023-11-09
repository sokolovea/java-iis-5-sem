package ru.rsreu.kuznecovsokolov12.datalayer.data;

public class SanctionType {
	
	private int id;
	private String name;
	
	public SanctionType() {
	}
	
	public SanctionType(int id, String name) {
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
