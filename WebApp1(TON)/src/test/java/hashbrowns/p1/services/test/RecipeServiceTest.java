package hashbrowns.p1.services.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import hashbrowns.p1.data.ORMImpl;
import hashbrowns.p1.exceptions.RecipeNameAlreadyExists;
import hashbrowns.p1.exceptions.UsernameAlreadyExistsException;
import hashbrowns.p1.models.Recipe;
import hashbrowns.p1.services.RecipeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
	
	@InjectMocks
	private RecipeServiceImpl recipeService;
	
	@Mock
	private ORMImpl orm;
	
	static List<Object> allRecipes;

	@BeforeAll
	public static void setUp() {
		allRecipes = new ArrayList<>();
		Recipe gordon = new Recipe();
		Recipe gertrude = new Recipe();
		Recipe gon = new Recipe();
		allRecipes.add(gordon);
		allRecipes.add(gertrude);
		allRecipes.add(gon);

	}
	
	@Test
	public void registerSuccsessfully() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		// set up 
		Recipe insertedRecipe = new Recipe(3,"Name","Yumm","Delicous","Food");
		
		Mockito.when(orm.insertObject(insertedRecipe)).thenReturn(insertedRecipe);
		
		// method calling
		Object obj = recipeService.registerObject(insertedRecipe);
		
		//assertion
		Assertions.assertNotNull(obj);
		Assertions.assertEquals(obj, insertedRecipe);
	}
	
	@Test
	@Disabled
	public void doNotRegisterException() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		//Exception Testing
		Recipe insertedRecipe = new Recipe(3,"Name","Yumm","Delicous","Food");
		
		Mockito.when(orm.insertObject(insertedRecipe)).thenReturn(new UsernameAlreadyExistsException());
		Object obj = recipeService.registerObject(insertedRecipe);
		
		Assertions.assertEquals(obj, new UsernameAlreadyExistsException());
	//	Assertions.assertThrowsExactly(obj, UsernameAlreadyExists());
		
	}
	
	@Test
	public void updateObject() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		
		Recipe insertedRecipe = new Recipe(3,"Name","Yumm","Delicous","Food");
		
		Mockito.when(orm.updateObject(insertedRecipe)).thenReturn(insertedRecipe);
		Object obj = recipeService.updateObject(insertedRecipe);
		
		Assertions.assertNotEquals(obj, null);
		
	}
	
	@Test
	public void cannotUpdateObject() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		
		Recipe insertedRecipe = new Recipe(3,"Name","Yumm","Delicous","Food");
		
		Mockito.when(orm.updateObject(insertedRecipe)).thenReturn(null);
		Object obj = recipeService.updateObject(insertedRecipe);
		
		Assertions.assertEquals(obj, null);
		
	}
	
	@Test
	public void findById() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		Recipe insertedRecipe = new Recipe(3,"Name","Yumm","Delicous","Food");
		
		Mockito.when(orm.findById(insertedRecipe)).thenReturn(insertedRecipe);
		Object obj = recipeService.findByID(insertedRecipe);
		
		Assertions.assertEquals(obj, insertedRecipe);
	}
	
	@Test
	public void doNotFindById() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		Recipe insertedRecipe = new Recipe(3,"Name","Yumm","Delicous","Food");
		
		Mockito.when(orm.findById(insertedRecipe)).thenReturn(null);
		Object obj = recipeService.findByID(insertedRecipe);
		
		Assertions.assertEquals(obj, null);
	}
	
	@Test
	public void doDelete() throws SQLException {
		Recipe recipeToDelete = new Recipe(3,"Name","Yumm","Delicous","Food");
		
		Mockito.when(orm.deleteObject(recipeToDelete)).thenReturn(recipeToDelete);
		Object obj = recipeService.deleteObj(recipeToDelete);
		
		Assertions.assertEquals(obj, recipeToDelete);
	}
	
	@Test
	public void doNotDelete() throws SQLException {
		Recipe recipeToDelete = new Recipe(3,"Name","Yumm","Delicous","Food");
		
		Mockito.when(orm.deleteObject(recipeToDelete)).thenReturn(null);
		Object obj = recipeService.deleteObj(recipeToDelete);
		
		Assertions.assertEquals(obj, null);
	}
	
	@Test 
	public void getAllInstances() throws SQLException{
		Recipe recipe = new Recipe(3,"Name","Yumm","Delicous","Food");
		
		Mockito.when(orm.getAll(recipe)).thenReturn(allRecipes);
		List<Object> allInstances = recipeService.findAll(recipe);
		
		Assertions.assertEquals(allInstances, allRecipes);
	}
	
}
