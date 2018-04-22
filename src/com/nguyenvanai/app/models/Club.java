package com.nguyenvanai.app.models;

public class Club extends AbstractEntity {

	private String venue;

	public Club(String id, String name, String venue) {
		super.setId(id);
		super.setName(name);
		this.venue = venue;

	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

}
