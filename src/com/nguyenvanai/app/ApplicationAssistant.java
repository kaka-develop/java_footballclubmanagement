package com.nguyenvanai.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nguyenvanai.app.managers.ClubManager;
import com.nguyenvanai.app.managers.ConversionManager;
import com.nguyenvanai.app.managers.GameManager;
import com.nguyenvanai.app.managers.PlayerManager;
import com.nguyenvanai.app.managers.TeamManager;
import com.nguyenvanai.app.models.AbstractEntity;
import com.nguyenvanai.app.models.Club;
import com.nguyenvanai.app.models.Game;
import com.nguyenvanai.app.models.Player;
import com.nguyenvanai.app.models.Team;

public class ApplicationAssistant {

	ClubManager clubManager = ClubManager.getInstance();
	TeamManager teamManager = TeamManager.getInstance();
	PlayerManager playerManager = PlayerManager.getInstance();
	GameManager gameManager = GameManager.getInstance();

	private static final ApplicationAssistant instance = new ApplicationAssistant();

	private ApplicationAssistant() {
		// loadDefaultData();
		loadDataFromFile();
	}

	public static ApplicationAssistant getInstance() {
		return instance;
	}

	// get all players
	@SuppressWarnings("unchecked")
	public List<Player> getAllPlayers() {
		return (List<Player>) (List<?>) playerManager.sortByID();
	}

	// add player with maximum = 34 in a team
	public boolean addPlayer(Player player) {
		if (getPlayersByTeamID(player.getTeamId()).size() >= 34)
			return false;
		return playerManager.add(player);
	}

	public List<Player> getPlayersByTeamID(String teamId) {
		List<Player> list = new ArrayList<>();
		for (Player player : getAllPlayers()) {
			if (player.getTeamId().equals(teamId))
				list.add(player);
		}
		return list;
	}

	public List<Player> getPlayersFromTeamForGame(String teamId) {
		List<Player> list = new ArrayList<>();
		List<Player> players = getPlayersByTeamID(teamId);
		int size = players.size() >= 16 ? 16: players.size() - 1;
		for (int i = 0; i <= size; i++) {
			Player p = players.get(i);
			if (i >= 12) {
				p.setStatus(PlayerManager.PLAYER_STATUS[1]);
			} else {
				p.setStatus(PlayerManager.PLAYER_STATUS[0]);
			}
			list.add(p);
		}
		return list;
	}

	// remove player
	public boolean removePlayer(String id) {
		return playerManager.delete(id);
	}

	// add Team with maximum = 34 in a club
	public boolean addTeam(Team team) {
		if (getTeamsByClubID(team.getClubId()).size() >= 4)
			return false;
		return teamManager.add(team);
	}

	// remove a team
	public boolean removeTeam(String id) {
		return teamManager.delete(id);
	}

	// add Team
	public boolean addGame(Game game) {
		return gameManager.add(game);
	}

	// remove a game
	public boolean removeGame(String id) {
		return gameManager.delete(id);
	}

	// change player's status
	public boolean changePlayerStatus(String id, int status) {
		return playerManager.changePlayerStatus(id, status);
	}

	// allocate a team to game
	public boolean allocateTeamToGame(String teamId, String gameId) {
		Game game = getGameByID(gameId);
		String date = ConversionManager.convertDateToStringWithoutSlashes(game.getDate());
		if (!game.getHomeTeamId().equals(teamId)) {
			game.setHomeTeamId(teamId);
			String id = gameManager.generateIDFromTeams(teamId, game.getAwayTeamId(), date);
			game.setId(id);
		} else if (!game.getAwayTeamId().equals(teamId)) {
			String id = gameManager.generateIDFromTeams(game.getHomeTeamId(), teamId, date);
			game.setId(id);
		} else
			return false;
		return true;
	}

	// get a team from player ID
	public Team getTeamByPlayerID(String id) {
		Player player = getPlayerByID(id);
		return getTeamByID(player.getTeamId());
	}

	// get a player by ID
	public Player getPlayerByID(String id) {
		return (Player) playerManager.get(id);
	}

	// validate player name
	public boolean validatePlayerName(String name) {
		return playerManager.validateName(name);
	}

	// get a team by ID
	public Team getTeamByID(String id) {
		return (Team) teamManager.get(id);
	}

	// get a Game by ID
	public Game getGameByID(String id) {
		return (Game) gameManager.get(id);
	}

	// get all clubs
	@SuppressWarnings("unchecked")
	public List<Club> getAllClubs() {
		return (List<Club>) (List<?>) clubManager.sortByID();
	}

	// check team Id whether exists or not
	public boolean isTeamIdExisted(String teamId) {
		return teamManager.isExisted(teamId);
	}

	// get player status
	public String getPlayerStatus(int parseInt) {

		return PlayerManager.PLAYER_STATUS[parseInt];
	}

	// generate player's ID from name
	public String generatePlayerIdFromName(String name) {
		return playerManager.generateIdFromName(name);
	}

	// check player ID whether exists or not
	public boolean isPlayerExisted(String id) {
		return playerManager.isExisted(id);
	}

	// check Club ID whether exists or not
	public boolean isClubIdExisted(String clubId) {
		return clubManager.isExisted(clubId);
	}

	// get all teams by club's ID
	public List<Team> getTeamsByClubID(String clubId) {
		List<Team> list = new ArrayList<>();
		for (AbstractEntity aTeam : teamManager.sortByID()) {
			Team team = (Team) aTeam;
			if (team.getClubId().equals(clubId))
				list.add(team);
		}
		return list;
	}

	// generate Team's ID from Name
	public String generateTeamIdFromName(String name) {
		return teamManager.generateIdFromName(name);
	}

	// get all games
	@SuppressWarnings("unchecked")
	public List<Game> getAllGames() {
		return (List<Game>) (List<?>) gameManager.sortByID();
	}

	// get club by team's ID
	public Club getClubByTeamID(String homeTeamId) {
		Team team = getTeamByID(homeTeamId);
		Club club = getClubByID(team.getClubId());
		return club;
	}

	public Club getClubByID(String clubId) {
		return (Club) clubManager.get(clubId);
	}

	public String generateGameID(String homeTeamId, String awayTeamId, String date) {
		return gameManager.generateIDFromTeams(homeTeamId, awayTeamId, date);
	}

	public String generateGameName(String homeTeamName, String awayTeamName) {
		return gameManager.generateNameFromTeams(homeTeamName, awayTeamName);
	}

	public boolean isGameExisted(String id) {
		return gameManager.isExisted(id);
	}

	public int countPossiblePlayers(String teamId) {
		int count = 0;
		for (Player player : getPlayersByTeamID(teamId)) {
			if (player.getStatus().equals(PlayerManager.PLAYER_STATUS[0])
					|| player.getStatus().equals(PlayerManager.PLAYER_STATUS[1]))
				count++;
		}
		return count;
	}

	// load default data for first time
	public void loadDefaultData() {
		clubManager.add(new Club("ZIM000CHE", "Chelsea FC", "Stamford Bridge"));
		clubManager.add(new Club("ZIM000LIV", "Liverpool FC", "Anfield"));
		clubManager.add(new Club("ZIM000MAN", "Manchester United FC", "Old Trafford"));

		teamManager.add(new Team("ZIM001MAI", "Main chelsea", "ZIM000CHE"));
		teamManager.add(new Team("ZIM001SUB", "Sub chelsea", "ZIM000CHE"));
		teamManager.add(new Team("ZIM001YOU", "Young Chelsea", "ZIM000CHE"));

		teamManager.add(new Team("ZIM002MAI", "Main Liverpool", "ZIM000LIV"));
		teamManager.add(new Team("ZIM002SUB", "Sub Liverpool", "ZIM000LIV"));
		teamManager.add(new Team("ZIM002YOU", "Young Liverpool", "ZIM000LIV"));

		playerManager.add(new Player("TTA0000", "Tran Tuan Anh", 19, PlayerManager.PLAYER_STATUS[1], "ZIM001MAI"));
		playerManager.add(new Player("NVT0000", "Nguyen Van Tuan", 20, PlayerManager.PLAYER_STATUS[2], "ZIM001MAI"));
		playerManager.add(new Player("NDA0000", "Nguyen Duc Anh", 28, PlayerManager.PLAYER_STATUS[3], "ZIM001MAI"));
		playerManager.add(new Player("NVN0000", "Ngo Van Ngoa", 31, PlayerManager.PLAYER_STATUS[1], "ZIM001MAI"));
		playerManager.add(new Player("TDT0000", "Tran Duc Thuan", 21, PlayerManager.PLAYER_STATUS[1], "ZIM001MAI"));

		playerManager.add(new Player("TTA0001", "Tran Tuan Anh", 19, PlayerManager.PLAYER_STATUS[1], "ZIM001SUB"));
		playerManager.add(new Player("NVT0001", "Nguyen Van Tuan", 20, PlayerManager.PLAYER_STATUS[2], "ZIM001SUB"));
		playerManager.add(new Player("NDA0001", "Nguyen Duc Anh", 28, PlayerManager.PLAYER_STATUS[3], "ZIM001SUB"));
		playerManager.add(new Player("NVN0001", "Ngo Van Ngoa", 31, PlayerManager.PLAYER_STATUS[1], "ZIM001SUB"));
		playerManager.add(new Player("TDT0001", "Tran Duc Thuan", 21, PlayerManager.PLAYER_STATUS[1], "ZIM001SUB"));

		playerManager.add(new Player("TTA0002", "Tran Tuan Anh", 19, PlayerManager.PLAYER_STATUS[1], "ZIM002MAI"));
		playerManager.add(new Player("NVT0002", "Nguyen Van Tuan", 20, PlayerManager.PLAYER_STATUS[2], "ZIM002MAI"));
		playerManager.add(new Player("NDA0002", "Nguyen Duc Anh", 28, PlayerManager.PLAYER_STATUS[3], "ZIM002MAI"));
		playerManager.add(new Player("NVN0002", "Ngo Van Ngoa", 31, PlayerManager.PLAYER_STATUS[1], "ZIM002MAI"));
		playerManager.add(new Player("TDT0002", "Tran Duc Thuan", 21, PlayerManager.PLAYER_STATUS[1], "ZIM002MAI"));

		Arrays.asList(
				"Nguyen Hai Name,Do Xuan Huy,Tran Tien Dat,Tran Quoc Khanh,To Van Khoi,Le Chi Quang,Tran Tuan Anh,Nguyen Van Hung,Nguyen Van Viet, Nguyen Van Thanh"
						.split(","))
				.forEach(name -> {
					playerManager.add(new Player(playerManager.generateIdFromName(name), name, 21, "ZIM001MAI"));
				});
		Arrays.asList(
				"Nguyen Hai Name,Do Xuan Huy,Tran Tien Dat,Tran Quoc Khanh,To Van Khoi,Le Chi Quang,Tran Tuan Anh,Nguyen Van Hung,Nguyen Van Viet, Nguyen Van Thanh"
						.split(","))
				.forEach(name -> {
					playerManager.add(new Player(playerManager.generateIdFromName(name), name, 23, "ZIM001MAI"));
				});
		Arrays.asList(
				"Nguyen Hai Name,Do Xuan Huy,Tran Tien Dat,Tran Quoc Khanh,To Van Khoi,Le Chi Quang,Tran Tuan Anh,Nguyen Van Hung,Nguyen Van Viet, Nguyen Van Thanh"
						.split(","))
				.forEach(name -> {
					playerManager.add(new Player(playerManager.generateIdFromName(name), name, 25, "ZIM002MAI"));
				});
		Arrays.asList(
				"Nguyen Hai Name,Do Xuan Huy,Tran Tien Dat,Tran Quoc Khanh,To Van Khoi,Le Chi Quang,Tran Tuan Anh,Nguyen Van Hung,Nguyen Van Viet, Nguyen Van Thanh"
						.split(","))
				.forEach(name -> {
					playerManager.add(new Player(playerManager.generateIdFromName(name), name, 26, "ZIM002MAI"));
				});

		Arrays.asList(
				"Nguyen Hai Name,Do Xuan Huy,Tran Tien Dat,Tran Quoc Khanh,To Van Khoi,Le Chi Quang,Tran Tuan Anh,Nguyen Van Hung,Nguyen Van Viet, Nguyen Van Thanh"
						.split(","))
				.forEach(name -> {
					playerManager.add(new Player(playerManager.generateIdFromName(name), name, 21, "ZIM001SUB"));
				});
		Arrays.asList(
				"Nguyen Hai Name,Do Xuan Huy,Tran Tien Dat,Tran Quoc Khanh,To Van Khoi,Le Chi Quang,Tran Tuan Anh,Nguyen Van Hung,Nguyen Van Viet, Nguyen Van Thanh"
						.split(","))
				.forEach(name -> {
					playerManager.add(new Player(playerManager.generateIdFromName(name), name, 18, "ZIM001SUB"));
				});
		Arrays.asList(
				"Nguyen Hai Name,Do Xuan Huy,Tran Tien Dat,Tran Quoc Khanh,To Van Khoi,Le Chi Quang,Tran Tuan Anh,Nguyen Van Hung,Nguyen Van Viet, Nguyen Van Thanh"
						.split(","))
				.forEach(name -> {
					playerManager.add(new Player(playerManager.generateIdFromName(name), name, 23, "ZIM002SUB"));
				});
		Arrays.asList(
				"Nguyen Hai Name,Do Xuan Huy,Tran Tien Dat,Tran Quoc Khanh,To Van Khoi,Le Chi Quang,Tran Tuan Anh,Nguyen Van Hung,Nguyen Van Viet, Nguyen Van Thanh"
						.split(","))
				.forEach(name -> {
					playerManager.add(new Player(playerManager.generateIdFromName(name), name, 25, "ZIM002SUB"));
				});

		Arrays.asList(
				"Nguyen Hai Name,Do Xuan Huy,Tran Tien Dat,Tran Quoc Khanh,To Van Khoi,Le Chi Quang,Tran Tuan Anh,Nguyen Van Hung,Nguyen Van Viet, Nguyen Van Thanh"
						.split(","))
				.forEach(name -> {
					playerManager.add(new Player(playerManager.generateIdFromName(name), name, 22, "ZIM001YOU"));
				});
		Arrays.asList(
				"Nguyen Hai Name,Do Xuan Huy,Tran Tien Dat,Tran Quoc Khanh,To Van Khoi,Le Chi Quang,Tran Tuan Anh,Nguyen Van Hung,Nguyen Van Viet, Nguyen Van Thanh"
						.split(","))
				.forEach(name -> {
					playerManager.add(new Player(playerManager.generateIdFromName(name), name, 23, "ZIM001YOU"));
				});
		Arrays.asList(
				"Nguyen Hai Name,Do Xuan Huy,Tran Tien Dat,Tran Quoc Khanh,To Van Khoi,Le Chi Quang,Tran Tuan Anh,Nguyen Van Hung,Nguyen Van Viet, Nguyen Van Thanh"
						.split(","))
				.forEach(name -> {
					playerManager.add(new Player(playerManager.generateIdFromName(name), name, 25, "ZIM002YOU"));
				});

		gameManager.add(new Game("001MAIv002MAI21102016", "Chelsea vs Liverpool", "ZIM002MAI", "ZIM001MAI"));
		gameManager.add(new Game("001SUBv002SUB21102016", "Chelsea vs Liverpool", "ZIM001SUB", "ZIM002SUB"));
		gameManager.add(new Game("001YOUv002YOU21102016", "Chelsea vs Liverpool", "ZIM001YOU", "ZIM001YOU"));

		saveData();
	}

	// load data from file
	public void loadDataFromFile() {
		try {
			playerManager.loadData();
			teamManager.loadData();
			clubManager.loadData();
			gameManager.loadData();
		} catch (Exception e) {
			loadDefaultData();
		}

	}

	// save data
	public void saveData() {
		playerManager.save();
		teamManager.save();
		clubManager.save();
		gameManager.save();
	}

}
