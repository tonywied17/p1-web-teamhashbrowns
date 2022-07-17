package hashbrowns.p1.data;

import java.util.List;

import hashbrowns.p1.exceptions.RecipeNameAlreadyExists;
import hashbrowns.p1.exceptions.UsernameAlreadyExistsException;

public interface ORM {
	
	/**
	 * Takes in an object to insert into DB
	 * Returns null and/or exceptions if user_name or 
	 * recipe name exists in the DB
	 *
	 *@param Object to be inserted
	 *@Return Object inserted or null
	 *@throws UsernameAlreadyExists, RecipeNameAlreadyExists
	 */
	public <T> Object insertObject(Object object) throws UsernameAlreadyExistsException, RecipeNameAlreadyExists;

	/**
	 * Takes in an object to delete from DB by ID
	 * Returns Deleted object
	 * 
	 *
	 *@param Object to be deleted
	 *@Return Object deleted or null
	 */
	public <T> Object deleteObject(Object object);
	
	/**
	 * Takes in an object to find table
	 * Then finds by ID
	 * 
	 *
	 *@param Object to find class/table
	 *@Return Object sought out
	 */
	public Object findById(Object object);
	
	/**
	 * Updates an Object by ID
	 * 
	 *
	 *@param Object to Update
	 *@Return Updated Object or null if ID not found
	 */
	public <T> Object updateObject(Object object);
	
	/**
	 * Gets all Objects from class/table
	 * 
	 *
	 *@param Object for class/table
	 *@Return List of object type
	 */
	public <T> List<T> getAll(Object object);



}
