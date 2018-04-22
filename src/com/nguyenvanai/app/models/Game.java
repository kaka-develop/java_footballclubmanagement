package com.nguyenvanai.app.models;

import java.util.Date;

public class Game extends AbstractEntity {


	private String venue;
	private Date date;
	private String homeTeamId;
	private String awayTeamId;
	
	public Game(String id, String name, String homeTeamId, String awayTeamId) {
		super.setId(id);
		super.setName(name);
		this.homeTeamId = homeTeamId;
		this.awayTeamId = awayTeamId;
		this.date = new Date();
	}
	public Game(String id, String name, String homeTeamId, String awayTeamId,Date date,String venue) {
		super.setId(id);
		super.setName(name);
		this.homeTeamId = homeTeamId;
		this.awayTeamId = awayTeamId;
		this.date = date;
		this.venue = venue;
	}

	public String getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(String homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public String getAwayTeamId() {
		return awayTeamId;
	}

	public void setAwayTeamId(String awayTeamId) {
		this.awayTeamId = awayTeamId;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	
}
