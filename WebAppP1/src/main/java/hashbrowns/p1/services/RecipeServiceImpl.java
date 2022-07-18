package hashbrowns.p1.services;

import java.util.List;

import hashbrowns.p1.utils.*;
import hashbrowns.p1.data.ORMImpl;
import hashbrowns.p1.exceptions.RecipeNameAlreadyExists;
import hashbrowns.p1.exceptions.UsernameAlreadyExistsException;

public class RecipeServiceImpl implements Service {
	
	ORMImpl orm = new ORMImpl();
	Logger logger = Logger.getLogger();


	@Override
	public Object registerObject(Object obj){
		try {
			obj = orm.insertObject(obj);
		} catch (UsernameAlreadyExistsException | RecipeNameAlreadyExists e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Object updateObject(Object obj) {
		obj = orm.updateObject(obj);
		return obj;
	}

	@Override
	public Object findByID(Object obj) {
		return orm.findById(obj);
	}

	@Override
	public List<Object> findAll(Object obj) {
		return orm.getAll(obj);
	
	}

	@Override
	public Object deleteObj(Object obj) {
		obj = orm.deleteObject(obj);
		return obj;
	}

}
