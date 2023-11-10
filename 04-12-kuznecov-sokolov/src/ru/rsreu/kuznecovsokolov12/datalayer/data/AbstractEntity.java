package ru.rsreu.kuznecovsokolov12.datalayer.data;

public abstract class AbstractEntity {

	private int id;
	
	protected AbstractEntity() {
		
	}
	
	protected AbstractEntity(int id) {
		this.setId(id);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
