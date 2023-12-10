package ru.rsreu.kuznecovsokolov12.datalayer.data;

/***
 * Type of interact with command
 * 
 * @author cuzne
 *
 */
public class TeamInteractType extends AbstractEntity {

	private String name;
	
	public TeamInteractType() {
	}
	
	public TeamInteractType(int id) {
		super(id);
	}
	
	public TeamInteractType(int id, String name) {
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
