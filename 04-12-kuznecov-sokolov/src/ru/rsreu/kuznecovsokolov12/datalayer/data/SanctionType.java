package ru.rsreu.kuznecovsokolov12.datalayer.data;

public class SanctionType extends AbstractEntity {
	
	private String name;
	
	public SanctionType() {
	}
	
	public SanctionType(int id) {
		super(id);
	}
	
	public SanctionType(int id, String name) {
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
