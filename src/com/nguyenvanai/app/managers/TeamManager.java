package com.nguyenvanai.app.managers;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.reflect.TypeToken;
import com.nguyenvanai.app.models.Team;

public class TeamManager extends AbstractManager{

	public static void main(String[] args) {
		TeamManager manager = TeamManager.getInstance();
		String id = manager.generateIdFromName("Chelsea");
		System.out.println(id);
	}
	private static final TeamManager instance = new TeamManager();
	private TeamManager() {
		
	}
	public static TeamManager getInstance() {
		return instance;
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return "teams.json";
	}

	@Override
	Type getListType() {
		Type listType = new TypeToken<Collection<Team>>() {
		}.getType();
		return listType;
	}

	@Override
	public boolean validateID(String id) {
		String regex = "^ZIM[0-9]{3}[A-Z]{3}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(id);
		return m.find();
	}
	public String generateIdFromName(String name) {
		name = name.trim().toUpperCase();
		StringBuilder sb = new StringBuilder();
		if(name.length()  >= 3){
			sb.append("ZIM");
			sb.append(String.format("%03d",count()));
			sb.append(name.substring(0, 3));
		}
		return sb.toString();
	}
	
	
}
