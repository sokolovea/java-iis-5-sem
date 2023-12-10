package ru.rsreu.kuznecovsokolov12.datalayer.data;

/***
 * Team that users can join
 * 
 * @author cuzne
 *
 */
public class Team extends AbstractEntity {
	
	private String name;
	
	public Team() {
	}

	public Team(int id) {
		super(id);
	}
	
	public Team(int id, String name) {
		this(id);
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
