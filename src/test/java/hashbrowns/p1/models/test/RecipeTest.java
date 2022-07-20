package hashbrowns.p1.models.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hashbrowns.p1.models.Chef;
import hashbrowns.p1.models.Recipe;

public class RecipeTest {
	

	private Recipe recipe = new Recipe(0,"h","ell","o", "0");
	private Recipe recipe1 = new Recipe();

	@Test 
	public void setConstructor() {
		Recipe test = new Recipe("h","hh","ello","he");
		Recipe test2 = new Recipe(2,"Hello","Im","bhjhjb","dkjd");
	}
	
	@Test
	public void testGetterMethods() {
		Assertions.assertEquals(0, recipe.getId());
		Assertions.assertEquals("h", recipe.getName());
		Assertions.assertEquals("ell", recipe.getDescription());
		Assertions.assertEquals("o", recipe.getInstructions());
		Assertions.assertEquals("0", recipe.getIngredients());
	}
	
	@Test 
	public void testSetterMethods() {
		recipe1.setId(0);
		recipe1.setName("h");
		recipe1.setDescription("ell");
		recipe1.setInstructions("o");
		recipe1.setIngredients("0");
		
		Assertions.assertEquals(0, recipe.getId());
		Assertions.assertEquals("h", recipe.getName());
		Assertions.assertEquals("ell", recipe.getDescription());
		Assertions.assertEquals("o", recipe.getInstructions());
		Assertions.assertEquals("0", recipe.getIngredients());
	}
	
	@Test
	public void testToString() {
	String test = "Recipe [id=" + recipe.getId() + ", username=" + recipe.getName() + ", description=" + recipe.getDescription()
				+ ", instructions=" + recipe.getInstructions() + ", ingredients=" + recipe.getIngredients() + "]";
	Assertions.assertEquals(recipe.toString(), test);
	}
	

}
