package hashbrowns.p1.models.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import hashbrowns.p1.models.Chef;

public class ChefTest {
	

	private Chef chef = new Chef(0,"h","ell","o");
	private Chef chef1 = new Chef();

	@Test 
	public void setConstructor() {
		Chef test = new Chef(1,"h","hh","ello");
		Chef tes1 = new Chef(2);
		Chef test2 = new Chef("Hello","Im","bhjhjb");
	}
	
	@Test
	public void testGetterMethods() {
		Assertions.assertEquals(0, chef.getId());
		Assertions.assertEquals("h", chef.getName());
		Assertions.assertEquals("ell", chef.getUsername());
		Assertions.assertEquals("o", chef.getPassword());
	}
	
	@Test 
	public void testSetterMethods() {
		chef1.setId(0);
		chef1.setName("h");
		chef1.setUsername("ell");
		chef1.setPassword("o");
		
		Assertions.assertEquals(0, chef1.getId());
		Assertions.assertEquals("h", chef1.getName());
		Assertions.assertEquals("ell", chef1.getUsername());
		Assertions.assertEquals("o", chef1.getPassword());	
	}
	
	@Test
	public void testToString() {
	String string = "Chef [id="+chef.getId()+", name=" +chef.getName()+ ", username=" + chef.getUsername() + ", password=" + chef.getPassword() + "]";
	Assertions.assertEquals(chef.toString(), string);	
	}
}
