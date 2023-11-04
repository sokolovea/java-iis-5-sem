package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

public class Setting extends AbstractEntity {

	private int id;
	private String name;
	private int value;
	
	public Setting() {
	}
	
	public Setting(int id, String name, int value) {
		this.setId(id);
		this.setName(name);
		this.setValue(value);
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
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
}
