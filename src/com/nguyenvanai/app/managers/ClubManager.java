package com.nguyenvanai.app.managers;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.reflect.TypeToken;
import com.nguyenvanai.app.models.Club;

public class ClubManager extends AbstractManager{
	
	private static final ClubManager instance = new ClubManager();
	private ClubManager() {
		
	}
	public static ClubManager getInstance() {
		return instance;
	}
	
	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return "clubs.json";
	}

	@Override
	Type getListType() {
		Type listType = new TypeToken<Collection<Club>>() {
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
