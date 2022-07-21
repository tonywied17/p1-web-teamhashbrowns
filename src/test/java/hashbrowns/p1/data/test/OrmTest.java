package hashbrowns.p1.data.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import hashbrowns.p1.models.Player;
import hashbrowns.p1.services.PlayerServiceImpl;
import hashbrowns.p1.utils.Connect;

@ExtendWith(MockitoExtension.class)
public class OrmTest {
	
	@InjectMocks
	private ORMImpl orm = new ORMImpl();
	
	@Mock
	private Chef chefRamsey;
	
	@Mock
	private PreparedStatement ps;
	
	@Mock
	private static Connection con;
	
	@Mock
	private ResultSet rs;
	
	@Mock 
	private Player player;
	
	
	private static List<Object> allObjects;
	
	@BeforeAll
	public static void setUp() throws SQLException {
		allObjects = new ArrayList<>();
		Object gordon = new Object();
		Object gertrude = new Object();
		Object gon = new Object();
		allObjects.add(gordon);
		allObjects.add(gertrude);
		allObjects.add(gon);
	}
	
	
	@Test
	public void insertObject() {
		// Set up 
		chefRamsey = new Chef(0,"uffff","tggggguff","sssssssmooh");
		StringBuilder sb = new StringBuilder();
		Object chef = null;
		try {
			chef = orm.insertObject(chefRamsey);
			Assertions.assertEquals(chefRamsey.toString(), chef.toString());
		} catch (UsernameAlreadyExistsException | RecipeNameAlreadyExists  e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void getAllObjects() {
		player = new Player(3,"Baseball","Baseball","Baseball",0.0,99,22,true);
		List<Object> list = orm.getAll(player);
		Assertions.assertNotNull(list);
		
	}
	
	@Test
	public void deleteObject() throws SQLException {
		
		player = new Player();
		player.setId(6);
		player.setName("Test");
		player.setPosition("LF");
		
		orm.deleteObject(player);
		
		Object delPlayer = orm.deleteObject(player);
		
		Assertions.assertNull(delPlayer);
		
		//Assertions.assertEquals(player, delPlayer);
		
	}
	
	@Test
	public void findById() throws SQLException {
		
		Chef chefRamsey = new Chef(5, "Hungarian Mike", "heh", "uff");

		Object chef = null;
		
		chef = orm.findById(chefRamsey);	
		
		Assertions.assertNotNull(chef);
		Assertions.assertEquals(chefRamsey.toString(), chef.toString());
	}
	
	@Test
	public void updateObj() {
		Chef chef = new Chef(3,"tony","ah","fmdmk");
		Object chef1 = null;
		
		chef1 = orm.updateObject(chef);
		
		Assertions.assertNotNull(chef1);
		Assertions.assertEquals(chef1.toString(), chef.toString());

	}

	
}
