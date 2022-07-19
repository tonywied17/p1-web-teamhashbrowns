package hashbrowns.p1.models.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hashbrowns.p1.models.Chef;
import hashbrowns.p1.models.Player;

public class PlayerTest {
	

	private Player player = new Player(0,"h","ell","o", 0.0, 0, 0,true);
	private Player player1 = new Player();

	@Test 
	public void setConstructor() {
		Player p = new Player(8);
		Player p1 = new Player(2,"j","e","o", 1.0, 0, 0,false);
	}
	
	
	@Test
	public void testGetterMethods() {
		Assertions.assertEquals(0, player.getId());
		Assertions.assertEquals("h", player.getName());
		Assertions.assertEquals("ell", player.getPosition());
		Assertions.assertEquals("o", player.getDebut());
		Assertions.assertEquals(0.0, player.getAverage());
		Assertions.assertEquals(0, player.getHomeruns());
		Assertions.assertEquals(0, player.getRbi());
		Assertions.assertEquals(true, player.isActive());
	}
	
	@Test 
	public void testSetterMethods() {
		player1.setId(0);
		player1.setName("h");
		player1.setPosition("ell");
		player1.setDebut("o");
		player1.setAverage(0.0);
		player1.setHomeruns(0);
		player1.setRbi(0);
		player1.setActive(true);
		Assertions.assertEquals(0, player1.getId());
		Assertions.assertEquals("h", player1.getName());
		Assertions.assertEquals("ell", player.getPosition());
		Assertions.assertEquals("o", player.getDebut());
		Assertions.assertEquals(0.0, player.getAverage());
		Assertions.assertEquals(0, player.getHomeruns());
		Assertions.assertEquals(0, player.getRbi());
		Assertions.assertEquals(true, player.isActive());	
	}
	
	@Test
	public void testToString() {
		String test = "Player [id=" + player.getId() + ", name=" + player.getName() + ", position=" + player.getPosition() + ", debut=" + player.getDebut() + ", average=" + player.getAverage()
				+ ", homeruns=" + player.getHomeruns() + ", rbi=" + player.getRbi() + ", active=" + player.isActive() + "]";
	Assertions.assertEquals(player.toString(), test);
	}
}
