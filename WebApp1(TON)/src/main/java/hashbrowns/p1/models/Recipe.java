package hashbrowns.p1.models;


import hashbrowns.p1.annotations.Id;

public class Recipe {

	
	@Id
	private int id;
	private String name, description, instructions, ingredients;
	
	public Recipe() {
		
	}

	public Recipe(String name, String description, String instructions,
			String ingredients) {
		super();
		this.name = name;
		this.description = description;
		this.instructions = instructions;
		this.ingredients = ingredients;
	}

	
	public Recipe(int id,String user, String description, String instructions,
			String ingredients) {
		super();
		this.id = id;
		this.name = user;
		this.description = description;
		this.instructions = instructions;
		this.ingredients = ingredients;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return name;
	}

	public void setUsername(String username) {
		this.name = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	
	@Override
	public String toString() {
		return "Recipe [id=" + id + ", username=" + name + ", description=" + description
				+ ", instructions=" + instructions + ", ingredients=" + ingredients + "]";
	}

}
