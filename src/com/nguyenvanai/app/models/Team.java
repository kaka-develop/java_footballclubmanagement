package com.nguyenvanai.app.models;

public class Team extends AbstractEntity {
	private String clubId;

	public Team(String id, String name,String clubId) {
		super.setId(id);
		super.setName(name);
		this.clubId = clubId;
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}


}
