package ru.rsreu.kuznecovsokolov12.datalayer.data;

/**
 * The {@code AbstractEntity} class serves as a superclass for all entity classes related to database tables.
 * It contains a single attribute, id, which uniquely identifies the entity in the database.
 *
 * <p>This abstract class provides methods for getting and setting the id attribute, as well as implementing
 * equals and hashCode methods based on the id for proper comparison and hashing in collections.
 */
public abstract class AbstractEntity {

	private int id;
	
	/**
	 * Constructs an AbstractEntity without initialized id.
	 */
	protected AbstractEntity() {
		
	}
	
	/**
	 * Constructs an AbstractEntity with the specified id.
	 *
	 * @param id The id to set for the entity.
	 */
	protected AbstractEntity(int id) {
		this.setId(id);
	}
	
	/**
	 * Gets the id of the entity.
	 *
	 * @return The id of the entity.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id of the entity.
	 *
	 * @param id The id to set for the entity.
	 */
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
