package ru.rsreu.kuznecovsokolov12.datalayer.data;


public class Setting extends AbstractEntity {

	private String name;
	private int value;
	
	public Setting() {
	}
	
	public Setting(int id) {
		super(id);
	}
	
	public Setting(int id, String name, int value) {
		this(id);
		this.setName(name);
		this.setValue(value);
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