package hashbrowns.p1.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import hashbrowns.p1.annotations.Id;

public class Player {

	@Id
	private int id;
	private String name;
	private String position;
	private String debut;
	private Double average;
	private int homeruns;
	private int rbi;
	private boolean active;

	public Player(int id, String name, String position, String debut, Double average, int homeruns, int rbi,
			boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.debut = debut;
		this.average = average;
		this.homeruns = homeruns;
		this.rbi = rbi;
		this.active = active;
	}

	public Player(int id) {
		super();
		this.id = id;
	}

	public Player() {

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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDebut() {
		return debut;
	}

	public void setDebut(String debut) {
		this.debut = debut;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	public int getHomeruns() {
		return homeruns;
	}

	public void setHomeruns(int homeruns) {
		this.homeruns = homeruns;
	}

	public int getRbi() {
		return rbi;
	}

	public void setRbi(int rbi) {
		this.rbi = rbi;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", position=" + position + ", debut=" + debut + ", average=" + average
				+ ", homeruns=" + homeruns + ", rbi=" + rbi + ", active=" + active + "]";
	}

}
