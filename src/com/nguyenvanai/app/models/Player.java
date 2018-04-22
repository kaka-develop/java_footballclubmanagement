package com.nguyenvanai.app.models;

public class Player extends AbstractEntity{
	
	private int age;
	private String status;
	private String teamId;
	

	public Player(String id, String name,int age, String status, String teamId) {
		super.setId(id);
		super.setName(name);
		this.age = age;
		this.status = status;
		this.teamId = teamId;
	}


	public Player(String id, String name,int age, String teamId) {
		super.setId(id);
		super.setName(name);
		this.age = age;
		this.teamId = teamId;
		this.status = "playing";
	}

	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}


	
	
}
