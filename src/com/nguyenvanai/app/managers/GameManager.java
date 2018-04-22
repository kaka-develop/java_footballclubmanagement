package com.nguyenvanai.app.managers;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.reflect.TypeToken;
import com.nguyenvanai.app.models.Game;

public class GameManager extends AbstractManager{

	private static final GameManager instance = new GameManager();
	private GameManager() {
		
	}
	public static GameManager getInstance() {
		return instance;
	}
	
	

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return "games.json";
	}

	@Override
	Type getListType() {
		Type listType = new TypeToken<Collection<Game>>() {
		}.getType();
		return listType;
	}

	@Override
	public boolean validateID(String id) {
		String regex = "^[0-9]{3}[A-Z]{3}v[0-9]{3}[A-Z]{3}[0-9]{8}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(id);
		return m.find();
	}
	
	public String generateIDFromTeams(String homeTeamId, String awayTeamId, String formatedDate){
		StringBuilder sb = new StringBuilder();
		if(homeTeamId.length() == 9 && awayTeamId.length() == 9 && formatedDate.length() == 8){
			sb.append(homeTeamId.substring(3, 9));
			sb.append("v");
			sb.append(awayTeamId.substring(3,9));
			sb.append(formatedDate);
		}
		return sb.toString();
	}
	
	public String generateNameFromTeams(String homeClubName, String awayClubName){
		StringBuilder sb = new StringBuilder();
		sb.append(homeClubName);
		sb.append(" vs ");
		sb.append(awayClubName);
		return sb.toString();
	}
}
