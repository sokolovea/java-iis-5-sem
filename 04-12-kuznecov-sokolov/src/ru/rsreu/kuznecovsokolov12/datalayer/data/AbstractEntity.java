package ru.rsreu.kuznecovsokolov12.datalayer.data;

/***
 * Abstract class for all entity, contains 1 attribute - id
 * 
 * @author Cuznecov
 *
 */
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		return this.getId() == other.getId();
	}

	@Override
	public int hashCode() {
		return this.getId();
	}
	
}
