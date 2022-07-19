package hashbrowns.p1.data.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import hashbrowns.p1.data.ORMImpl;
import hashbrowns.p1.exceptions.RecipeNameAlreadyExists;
import hashbrowns.p1.exceptions.UsernameAlreadyExistsException;
import hashbrowns.p1.models.Chef;
import hashbrowns.p1.utils.Connect;

public class OrmTest {
	
	@InjectMocks
	private ORMImpl orm = new ORMImpl();
	
	@Mock
	private Chef chef;
	
	@Mock
	private PreparedStatement ps;
	
	@Mock
	private Connect connection;
	
	@Mock
	private Connection con;
	
	private static List<Object> allObjects;
	
	@BeforeAll
	public static void setUp() {
		allObjects = new ArrayList<>();
		Object gordon = new Object();
		Object gertrude = new Object();
		Object gon = new Object();
		allObjects.add(gordon);
		allObjects.add(gertrude);
		allObjects.add(gon);

	}
	
	@Test
	@Disabled
	public void insertObject() throws UsernameAlreadyExistsException, RecipeNameAlreadyExists, SQLException {
		
		// Set up 
		Chef chefRamsey = new Chef(0,"uff","tuff","smooh");
		// Method Calling
		Object chefOutcome = orm.insertObject(chefRamsey);
		// Assertion
		Assertions.assertEquals(chefRamsey, chefOutcome);
		
	}

	
}
