package hashbrowns.p1.services;

import java.sql.SQLException;
import java.util.List;

import hashbrowns.p1.exceptions.UsernameAlreadyExistsException;

public interface Service {

	/**
	 * Creates a Object and instance in the Database if user-name does not already
	 * exist. Returns the newly created instance
	 * 
	 * 
	 * @param User being taken in
	 * @return The created user
	 * 
	 * @throws SQLException 
	 */
	public Object registerObject(Object obj) throws SQLException;

	/**
	 * Updates an instance by and ID and sets values to whatever The field's are set
	 * to in the object
	 * 
	 * 
	 * @param Object to be updated
	 * @return The updated object or null if object doesn't exist
	 */
	public Object updateObject(Object obj);

	/**
	 * Finds Object by ID returns null if not found in DB
	 * 
	 * 
	 * @param Object to indexed
	 * @return The indexed object or null
	 */
	public Object findByID(Object obj);

	/**
	 * Finds all Objects in DB
	 * 
	 * 
	 * @param Object type to indexed
	 * @return List of Object type
	 */
	public List<Object> findAll(Object obj);

	/**
	 * Delete's object in DB
	 * 
	 * 
	 * @param Object to be deleted
	 * @return Object if it was deleted null if it wasn't
	 */
	public Object deleteObj(Object obj);

}
