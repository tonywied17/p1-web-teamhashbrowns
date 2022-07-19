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
import hashbrowns.p1.models.Player;
import hashbrowns.p1.services.PlayerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
	
	@InjectMocks
	private PlayerServiceImpl playerService;
	
	@Mock
	private ORMImpl orm;
	
	static List<Object> allPlayers;

	@BeforeAll
	public static void setUp() {
		allPlayers = new ArrayList<>();
		Player gordon = new Player();
		Player gertrude = new Player();
		Player gon = new Player();
		allPlayers.add(gordon);
		allPlayers.add(gertrude);
		allPlayers.add(gon);

	}
	
	@Test
	public void registerSuccsessfully() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		// set up 
		Player insertedPlayer = new Player(3,"Baseball","Baseball","Baseball",0.0,99,22,true);
		
		Mockito.when(orm.insertObject(insertedPlayer)).thenReturn(insertedPlayer);
		
		// method calling
		Object obj = playerService.registerObject(insertedPlayer);
		
		//assertion
		Assertions.assertNotNull(obj);
		Assertions.assertEquals(obj, insertedPlayer);
	}
	
	@Test
	@Disabled
	public void doNotRegisterException() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		//Exception Testing
		Player insertedPlayer = new Player(3,"Baseball","Baseball","Baseball",0.0,99,22,true);
		
		Mockito.when(orm.insertObject(insertedPlayer)).thenReturn(new UsernameAlreadyExistsException());
		Object obj = playerService.registerObject(insertedPlayer);
		
		Assertions.assertEquals(obj, new UsernameAlreadyExistsException());
	//	Assertions.assertThrowsExactly(obj, UsernameAlreadyExists());
		
	}
	
	@Test
	public void updateObject() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		
		Player insertedPlayer = new Player(3,"Baseball","Baseball","Baseball",0.0,99,22,true);
		
		Mockito.when(orm.updateObject(insertedPlayer)).thenReturn(insertedPlayer);
		Object obj = playerService.updateObject(insertedPlayer);
		
		Assertions.assertNotEquals(obj, null);
		
	}
	
	@Test
	public void cannotUpdateObject() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		
		Player insertedPlayer = new Player(3,"Baseball","Baseball","Baseball",0.0,99,22,true);
		
		Mockito.when(orm.updateObject(insertedPlayer)).thenReturn(null);
		Object obj = playerService.updateObject(insertedPlayer);
		
		Assertions.assertEquals(obj, null);
		
	}
	
	@Test
	public void findById() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		Player insertedPlayer = new Player(3,"Baseball","Baseball","Baseball",0.0,99,22,true);
		
		Mockito.when(orm.findById(insertedPlayer)).thenReturn(insertedPlayer);
		Object obj = playerService.findByID(insertedPlayer);
		
		Assertions.assertEquals(obj, insertedPlayer);
	}
	
	@Test
	public void doNotFindById() throws UsernameAlreadyExistsException, SQLException, RecipeNameAlreadyExists{
		Player insertedPlayer = new Player(3,"Baseball","Baseball","Baseball",0.0,99,22,true);
		
		Mockito.when(orm.findById(insertedPlayer)).thenReturn(null);
		Object obj = playerService.findByID(insertedPlayer);
		
		Assertions.assertEquals(obj, null);
	}
	
	@Test
	public void doDelete() throws SQLException {
		Player playerToDelete = new Player(3,"Baseball","Baseball","Baseball",0.0,99,22,true);
		
		Mockito.when(orm.deleteObject(playerToDelete)).thenReturn(playerToDelete);
		Object obj = playerService.deleteObj(playerToDelete);
		
		Assertions.assertEquals(obj, playerToDelete);
	}
	
	@Test
	public void doNotDelete() throws SQLException {
		Player playerToDelete = new Player(3,"Baseball","Baseball","Baseball",0.0,99,22,true);
		
		Mockito.when(orm.deleteObject(playerToDelete)).thenReturn(null);
		Object obj = playerService.deleteObj(playerToDelete);
		
		Assertions.assertEquals(obj, null);
	}
	
	@Test 
	public void getAllInstances() throws SQLException{
		Player player = new Player(3,"Baseball","Baseball","Baseball",0.0,99,22,true);
		
		Mockito.when(orm.getAll(player)).thenReturn(allPlayers);
		List<Object> allInstances = playerService.findAll(player);
		
		Assertions.assertEquals(allInstances, allPlayers);
	}
	
}
