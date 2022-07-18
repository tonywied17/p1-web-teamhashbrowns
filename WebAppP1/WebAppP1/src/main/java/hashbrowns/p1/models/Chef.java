package hashbrowns.p1.models;

import java.util.List;

import hashbrowns.p1.annotations.Id;

public class Chef {

	@Id
	private int id;
	private String name, username, password;

	public Chef() {
	}
	
	public Chef(int id) {
		super();
		this.id = id;
	}

	public Chef(String name, String username, String password) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;

	
	}
	
	public Chef(int id, String name, String username, String password) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Chef [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + "]";
	}

	

	
	
}
