package com.nguyenvanai.app.managers;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.reflect.TypeToken;
import com.nguyenvanai.app.models.Player;

public class PlayerManager extends AbstractManager {

	public static void main(String[] args) {
		PlayerManager manager = PlayerManager.getInstance();
		String id = manager.generateIdFromName("nguyen van ai");
		System.out.println(id);
	}

	public static final String[] PLAYER_STATUS = { "playing", "substitute", "injured", "in transfer" };

	private static final PlayerManager instance = new PlayerManager();

	public static PlayerManager getInstance() {
		return instance;
	}

	public boolean changePlayerStatus(String id, int status) {
		if (!isExisted(id))
			return false;
		Player player = (Player) get(id);
		player.setStatus(PLAYER_STATUS[status]);
		return true;
	}

	public boolean validateName(String name) {
		String regex = "^\\w*\\s\\w*\\s\\w*$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(name);
		return m.find();
	}

	public String generateIdFromName(String name) {
		StringBuilder sb = new StringBuilder();
		if (validateName(name)) {
			name = name.toUpperCase();
			String parts[] = name.split("\\s");
			sb.append(parts[0].charAt(0));
			sb.append(parts[1].charAt(0));
			sb.append(parts[2].charAt(0));
			sb.append(String.format("%04d", count()));
		}

		return sb.toString();
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return "players.json";
	}

	@Override
	Type getListType() {
		Type listType = new TypeToken<Collection<Player>>() {
		}.getType();
		return listType;
	}

	@Override
	public boolean validateID(String id) {
		String regex = "^[A-Z]{3}[0-9]{4}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(id);
		return m.find();
	}
}
