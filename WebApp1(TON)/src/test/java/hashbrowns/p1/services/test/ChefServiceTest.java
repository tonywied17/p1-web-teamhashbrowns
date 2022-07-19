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
import hashbrowns.p1.models.Chef;
import hashbrowns.p1.services.ChefServiceImpl;
import hashbrowns.p1.services.PlayerServiceImpl;
import hashbrowns.p1.services.RecipeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ChefServiceTest {
	
	@InjectMocks
	private ChefServiceImpl chefService;
	@InjectMocks
	private Chef chef;
	@InjectMocks
	private RecipeServiceImpl recipeService;
	@InjectMocks
	private PlayerServiceImpl playerService;

	@Mock
	private ORMImpl orm;
	
	static List<Object> allChefs;

	@BeforeAll
	public static void setUp() {
		allChefs = new ArrayList<>();
		Chef gordon = new Chef();
		Chef gertrude = new Chef();
		Chef gon = new Chef();
		allChefs.add(gordon);
		allChefs.add(gertrude);
		allChefs.add(gon);

	}
	
	@Test
	public void registerSuccsessfully() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		// set up 
		Chef insertedChef = new Chef(0,"t","e","s");
		Mockito.when(orm.insertObject(insertedChef)).thenReturn(insertedChef);
		// method calling
		Object obj = chefService.registerObject(insertedChef);
		//assertion
		Assertions.assertNotNull(obj);
		Assertions.assertEquals(obj, insertedChef);
	}
	
	@Test
	@Disabled
	public void doNotRegisterException() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		//Exception Testing
		Chef insertedChef = new Chef(0,"ID","Already","Exist");
		
		Mockito.when(orm.insertObject(insertedChef)).thenReturn(new UsernameAlreadyExistsException());
		Object obj = chefService.registerObject(insertedChef);
		
		Assertions.assertEquals(obj, new UsernameAlreadyExistsException());
	//	Assertions.assertThrowsExactly(obj, UsernameAlreadyExists());
		
	}
	
	@Test
	public void updateObject() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		
		Chef insertedChef = new Chef(9,"je","changed-username","changed-passcode");
		
		Mockito.when(orm.updateObject(insertedChef)).thenReturn(insertedChef);
		Object obj = chefService.updateObject(insertedChef);
		
		Assertions.assertNotEquals(obj, null);
		
	}
	
	@Test
	public void cannotUpdateObject() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		
		Chef initialChef = new Chef(99,"Couldnt","Find","Chef");
		
		Mockito.when(orm.updateObject(initialChef)).thenReturn(null);
		Object obj = chefService.updateObject(initialChef);
		
		Assertions.assertEquals(obj, null);
		
	}
	
	@Test
	public void findById() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		Chef insertedChef = new Chef (9,"Find","My","ID");
		
		Mockito.when(orm.findById(insertedChef)).thenReturn(insertedChef);
		Object obj = chefService.findByID(insertedChef);
		
		Assertions.assertEquals(obj, insertedChef);
	}
	
	@Test
	public void doNotFindById() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		Chef insertedChef = new Chef (9,"Find","My","ID");
		
		Mockito.when(orm.findById(insertedChef)).thenReturn(null);
		Object obj = chefService.findByID(insertedChef);
		
		Assertions.assertEquals(obj, null);
	}
	
	@Test
	public void doDelete() throws SQLException {
		Chef chefToDelete = new Chef(0,"Please","Delete","Me");
		
		Mockito.when(orm.deleteObject(chefToDelete)).thenReturn(chefToDelete);
		Object obj = chefService.deleteObj(chefToDelete);
		
		Assertions.assertEquals(obj, chefToDelete);
	}
	
	@Test
	public void doNotDelete() throws SQLException {
		Chef chefToDelete = new Chef(0,"Chef","Not","Found");
		
		Mockito.when(orm.deleteObject(chefToDelete)).thenReturn(null);
		Object obj = chefService.deleteObj(chefToDelete);
		
		Assertions.assertEquals(obj, null);
	}
	
	@Test 
	public void getAllInstances() throws SQLException{
		Chef chef = new Chef(0,"Looking","All Rows","In Table");
		
		Mockito.when(orm.getAll(chef)).thenReturn(allChefs);
		List<Object> allInstances = chefService.findAll(chef);
		
		Assertions.assertEquals(allInstances, allChefs);
	}
	
}
