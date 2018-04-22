package com.nguyenvanai.app;

import java.util.Date;
import java.util.Scanner;

import com.nguyenvanai.app.managers.ConversionManager;
import com.nguyenvanai.app.managers.PlayerManager;
import com.nguyenvanai.app.models.Club;
import com.nguyenvanai.app.models.Game;
import com.nguyenvanai.app.models.Player;
import com.nguyenvanai.app.models.Team;

public class ApplicationMenu {
	private ApplicationAssistant assistant = ApplicationAssistant.getInstance();
	private Scanner sc = new Scanner(System.in);

	public void start() {
		main_menu();
	}

	private void main_menu() {
		while (true) {
			System.out.println("======Zimbuye Football System=====");
			System.out.println("1. View clubs");
			System.out.println("2. Manage players");
			System.out.println("3. Manage teams in a club");
			System.out.println("4. Manage games");
			System.out.println("5. Exit");
			System.out.print("Please enter: ");
			int choice = makeIntScanner();

			switch (choice) {
			case 1:
				menu_viewAllClubs();
				break;
			case 2:
				menu_player();
				break;
			case 3:
				menu_team();
				break;
			case 4:
				menu_game();
				break;
			case 5:
				menu_exit();
				return;
			default:
				break;
			}
		}
	}

	private void menu_exit() {
		while (true) {
			System.out.print("Do you want to save data? (y/n): ");
			String choice = sc.nextLine();
			if (choice.equalsIgnoreCase("y"))
				assistant.saveData();

			System.out.println("Exit!");
			return;
		}
	}

	private void menu_viewAllClubs() {
		System.out.println("=======All Clubs========");
		String format = "%-20s%-30s%-1s";
		System.out.format(format, "ID", "Name", "Venue\n");
		for (Club club : assistant.getAllClubs()) {
			System.out.format(format, club.getId(), club.getName(), club.getVenue() + "\n");
		}

	}

	private int makeIntScanner() {
		int choice = 0;
		while (!sc.hasNextInt()) {
			System.out.print("Please (integer) enter: ");
			sc.nextLine();
		}
		choice = sc.nextInt();
		sc.nextLine();
		return choice;
	}

	private void menu_player() {
		while (true) {
			System.out.println("======Players Management=====");
			System.out.println("1. View all players");
			System.out.println("2. View a player in details");
			System.out.println("3. Add new player");
			System.out.println("4. Remove player");
			System.out.println("5. Modify player's status");
			System.out.println("6. Back");
			System.out.print("Please enter: ");
			int choice = makeIntScanner();
			switch (choice) {
			case 1:
				menu_viewAllPlayers();
				break;
			case 2:
				menu_viewPlayerDetails();
				break;
			case 3:
				menu_addNewPlayer();
				break;
			case 4:
				menu_removePlayer();
				break;
			case 5:
				menu_changeStatusPlayer();
				break;
			case 6:
				return;
			default:
				break;
			}
		}

	}

	private void menu_viewPlayerDetails() {
		System.out.println("=======Player Details========");
		System.out.print("Player ID: ");
		String id = sc.nextLine();
		if (assistant.isPlayerExisted(id)) {
			Player player = assistant.getPlayerByID(id);
			System.out.println("Name: " + player.getName());
			System.out.println("Age: " + player.getAge());
			System.out.println("Status: " + player.getStatus());
			System.out.println("Team: " + assistant.getTeamByID(player.getTeamId()).getName());
			String clubName = assistant.getClubByID(assistant.getTeamByID(player.getTeamId()).getClubId()).getName();
			System.out.println("Club: " + clubName);
		} else
			System.out.println("Failed! This ID does not exist.");
	}

	private void menu_changeStatusPlayer() {
		System.out.println("=======Modify Player's Status========");
		System.out.print("ID: ");
		String id = sc.nextLine();
		if (assistant.isPlayerExisted(id)) {
			String status = assistant.getPlayerByID(id).getStatus();
			status = menu_validateStatusInput(status);
			if (assistant.changePlayerStatus(id, Integer.parseInt(status)))
				System.out.println("Modify player's status successfully!");

		} else
			System.out.println("Failed! This ID does not exist.");
	}

	private void menu_removePlayer() {
		System.out.println("=======Remove Player========");
		System.out.print("ID: ");
		String id = sc.nextLine();
		if (assistant.removePlayer(id))
			System.out.println("Remove player successfully!");
		else
			System.out.println("Failed! This ID does not exist.");
	}

	private String menu_validateStatusInput(String oldStatus) {
		System.out.println("0:playing, 1:substitude, 2:injured, 3:in transfer.");
		String status = "";
		while (status.isEmpty()) {
			System.out.print("Status: " + oldStatus + "->");
			status = sc.nextLine();
			try {
				if (Integer.parseInt(status) > 3 || Integer.parseInt(status) < 0) {
					System.out.println("Please follow the hint (0, 1, 2 or 3)");
					status = "";
				}
			} catch (Exception e) {
				System.out.println("Please follow the hint (0, 1, 2 or 3)");
				status = "";
			}
		}
		return status;
	}

	private void menu_addNewPlayer() {
		System.out.println("=======Add New Player========");
		String name = "";
		while (name.isEmpty()) {
			System.out.print("Name: ");
			name = sc.nextLine();
			if (!assistant.validatePlayerName(name)) {
				System.out.println("Please follow the example: Nguyen Van Son");
				name = "";
			}
		}

		String id = assistant.generatePlayerIdFromName(name);
		System.out.println("ID: " + id);

		String age = "";
		while (age.isEmpty()) {
			System.out.print("Age: ");
			age = sc.nextLine();
			try {
				if (Integer.parseInt(age) < 15 || Integer.parseInt(age) > 35) {
					System.out.println("Please follow the hint(>15 and <35)");
					age = "";
				}
			} catch (Exception e) {
				System.out.println("Please follow the hint(>15 and <35)");
				age = "";
			}
		}

		String teamId = "";

		while (teamId.isEmpty()) {
			System.out.print("Team ID: ");
			teamId = sc.nextLine();
			try {
				if (!assistant.isTeamIdExisted(teamId)) {
					System.out.println("This ID does not exist");
					teamId = "";
				}
			} catch (Exception e) {
				System.out.println("This ID does not exist");
				teamId = "";
			}
		}

		Player player = new Player(id, name, Integer.parseInt(age), teamId);
		if (assistant.addPlayer(player)) {
			System.out.println("Add new player sucessfully!");
		} else {
			System.out.println("Failed! Max players in a team = 34 players.");
			System.out.println(player.getId());
		}
	}

	private void menu_viewAllPlayers() {
		System.out.println("=======All Players========");
		String format = "%-10s%-20s%-10s%-20s%-1s";
		System.out.format(format, "ID", "Name", "Age", "Status", "Team\n");
		for (Player player : assistant.getAllPlayers()) {
			System.out.format(format, player.getId(), player.getName(), player.getAge(), player.getStatus(),
					assistant.getTeamByID(player.getTeamId()).getName() + "\n");
		}
	}

	private void menu_team() {
		System.out.print("Club ID: ");
		String clubId = sc.nextLine();
		if (assistant.isClubIdExisted(clubId)) {

			while (true) {
				System.out.println("======Teams Management in a Club=====");
				System.out.println("1. View all teams");
				System.out.println("2. Add new team");
				System.out.println("3. Remove team");
				System.out.println("4. Back");
				System.out.print("Please enter: ");
				int choice = makeIntScanner();
				switch (choice) {
				case 1:
					menu_viewAllTeams(clubId);
					break;
				case 2:
					menu_addNewTeam(clubId);
					break;
				case 3:
					menu_removeTeam(clubId);
					break;
				case 4:
					return;
				default:
					break;
				}
			}
		} else
			System.out.println("This club ID does not exist!");
	}

	private void menu_viewAllTeams(String clubId) {
		System.out.println("=====View teams in the club=====");
		String format = "%-20s%-1s";
		System.out.format(format, "ID", "Name\n");
		for (Team team : assistant.getTeamsByClubID(clubId)) {
			System.out.format(format, team.getId(), team.getName() + "\n");
		}
	}

	private void menu_removeTeam(String clubId) {
		System.out.println("=====Remove teams from club=====");
		System.out.print("Team ID: ");
		String teamId = sc.nextLine();
		if (assistant.isTeamIdExisted(teamId)) {
			if (assistant.removeTeam(teamId)) {
				System.out.println("Remove a team successfully!");
			}
		} else
			System.out.println("This team ID does not exist!");
	}

	private void menu_addNewTeam(String clubId) {
		System.out.println("=======Add New Team to the club========");
		String name = "";
		while (name.isEmpty()) {
			System.out.print("Name: ");
			name = sc.nextLine();
			if (name.trim().length() < 3) {
				System.out.println("Please follow the example: Junior Chelsea");
				name = "";
			}
		}

		String id = assistant.generateTeamIdFromName(name);
		System.out.println("ID: " + id);

		if (assistant.addTeam(new Team(id, name, clubId)))
			System.out.println("Add new steam successfully!");
		else
			System.out.println("Failed! Max teams in club = 4 teams.");
	}

	private void menu_game() {
		while (true) {
			System.out.println("======Games Management=====");
			System.out.println("1. View all games");
			System.out.println("2. View a game in details");
			System.out.println("3. Add new game");
			System.out.println("4. Remove game");
			System.out.println("5. Allocate a team to game");
			System.out.println("6. Allocate a player to game");
			System.out.println("7. Back");
			System.out.print("Please enter: ");
			int choice = makeIntScanner();
			switch (choice) {
			case 1:
				menu_viewAllGames();
				break;
			case 2:
				menu_viewGameDetail();
				break;
			case 3:
				menu_addNewGame();
				break;
			case 4:
				menu_removeGame();
				break;
			case 5:
				menu_allocateTeamForGame();
				break;
			case 6:
				menu_allocatePlayerForGame();
				break;
			case 7:
				return;
			default:
				break;
			}
		}

	}

	private void menu_viewGameDetail() {
		System.out.println("=======Game details========");
		System.out.print("Game's ID: ");
		String id = sc.nextLine();
		if (assistant.isGameExisted(id)) {
			Game game = assistant.getGameByID(id);
			System.out.println("Name: " + game.getName());
			System.out.println("Venue: " + game.getVenue());
			System.out.println("Date: " + ConversionManager.convertDateToString(game.getDate()));
			Team homeTeam = assistant.getTeamByID(game.getHomeTeamId());
			Team awayTeam = assistant.getTeamByID(game.getAwayTeamId());
			System.out.println(homeTeam.getName() + " players:");
			String format = "%-10s%-20s%-10s%-1s";

			System.out.format(format, "ID", "Name", "Age", "Status\n");
			for (Player player : assistant.getPlayersFromTeamForGame(homeTeam.getId())) {
				System.out.format(format, player.getId(), player.getName(), player.getAge(), player.getStatus() + "\n");
			}

			System.out.println(awayTeam.getName() + " players:");
			for (Player player : assistant.getPlayersFromTeamForGame(awayTeam.getId())) {
				System.out.format(format, player.getId(), player.getName(), player.getAge(), player.getStatus() + "\n");
			}

		} else
			System.out.println("This ID does not exist");
	}

	private void menu_allocateTeamForGame() {
		System.out.println("=======Allocate a team for game========");
		String teamId = "";
		while (teamId.isEmpty()) {
			System.out.print("Team's ID: ");
			teamId = sc.nextLine();
			try {
				if (!assistant.isTeamIdExisted(teamId)) {
					System.out.println("This ID does not exist");
					teamId = "";
				}
			} catch (Exception e) {
				System.out.println("This ID does not exist");
				teamId = "";
			}
		}

		String gameId = "";
		while (gameId.isEmpty()) {
			System.out.print("Game's ID: ");
			gameId = sc.nextLine();
			try {
				if (!assistant.isGameExisted(gameId)) {
					System.out.println("This ID does not exist");
					gameId = "";
				}
			} catch (Exception e) {
				System.out.println("This ID does not exist");
				gameId = "";
			}
		}

		if (assistant.allocateTeamToGame(teamId, gameId))
			System.out.println("Allocate a team for game successfully!");
		else
			System.out.println("Failed! The club of team does not have this game.");

	}

	private void menu_allocatePlayerForGame() {

		System.out.println("=======Allocate a player for game========");
		System.out.print("Player ID: ");
		String playerId = sc.nextLine();
		if (assistant.isPlayerExisted(playerId)) {
			Player player = assistant.getPlayerByID(playerId);
			if (player.getStatus().equals(PlayerManager.PLAYER_STATUS[2])
					|| player.getStatus().equals(PlayerManager.PLAYER_STATUS[3])) {
				System.out.println("Failed! The player is " + player.getStatus());
			} else {
				System.out.println("Player name: " + player.getName());
				System.out.print("Game ID: ");
				String gameId = sc.nextLine().trim();
				if (assistant.isGameExisted(gameId)) {
					Game game = assistant.getGameByID(gameId);
					System.out.println("Game name: " + game.getName());
					if (player.getTeamId().equals(game.getHomeTeamId())
							|| player.getTeamId().equals(game.getAwayTeamId())) {
						player.setStatus(PlayerManager.PLAYER_STATUS[0]);
						System.out.println("Allocate a player for game successfully!");
					} else
						System.out.println("Failed! The player's team does not play this game!");

				} else
					System.out.println("Failed! This ID does not exist.");
			}
		} else
			System.out.println("Failed! This ID does not exist.");
	}

	private void menu_viewAllGames() {
		System.out.println("=======View all games========");
		String format = "%-25s%-25s%-15s%-15s%-15s%-1s";
		System.out.format(format, "ID", "Name", "Home team", "Away team", "Date", "Venue\n");
		for (Game game : assistant.getAllGames()) {
			String date = ConversionManager.convertDateToString(game.getDate());
			String venue = assistant.getClubByTeamID(game.getHomeTeamId()).getVenue();
			System.out.format(format, game.getId(), game.getName(), game.getHomeTeamId(), game.getAwayTeamId(), date,
					venue + "\n");
		}

	}

	private void menu_addNewGame() {
		System.out.println("=========Add new game==========");
		String homeTeamId = "";
		while (homeTeamId.isEmpty()) {
			System.out.print("Home team's ID: ");
			homeTeamId = sc.nextLine();
			try {
				if (!assistant.isTeamIdExisted(homeTeamId)) {
					System.out.println("This ID does not exist");
					homeTeamId = "";
				}
			} catch (Exception e) {
				System.out.println("This ID does not exist");
				homeTeamId = "";
			}
		}

		if (assistant.countPossiblePlayers(homeTeamId) < 16) {
			System.out.println("This team does not have enough players to play (>16 players)");
			return;
		}

		String awayTeamId = "";
		while (awayTeamId.isEmpty()) {
			System.out.print("Away team's ID: ");
			awayTeamId = sc.nextLine();
			try {
				if (!assistant.isTeamIdExisted(awayTeamId)) {
					System.out.println("This ID does not exist");
					awayTeamId = "";
				} else if (awayTeamId.equals(homeTeamId)) {
					System.out.println("This ID is home team's ID");
					awayTeamId = "";
				} else if (assistant.getTeamByID(homeTeamId).getClubId()
						.equals(assistant.getTeamByID(awayTeamId).getClubId())) {
					System.out.println("This team is in the same club with home team.");
					awayTeamId = "";
				}
			} catch (Exception e) {
				System.out.println("This ID does not exist");
				awayTeamId = "";
			}
		}

		if (assistant.countPossiblePlayers(awayTeamId) < 16) {
			System.out.println("This team does not have enough players to play (>16 players)");
			return;
		}

		Date date = new Date();
		String id = assistant.generateGameID(homeTeamId, awayTeamId,
				ConversionManager.convertDateToStringWithoutSlashes(date));
		System.out.println("ID: " + id);
		Club homeClub = assistant.getClubByID(assistant.getTeamByID(homeTeamId).getClubId());
		Club awayClub = assistant.getClubByID(assistant.getTeamByID(awayTeamId).getClubId());
		String name = assistant.generateGameName(homeClub.getName(), awayClub.getName());
		System.out.println("Name: " + name);
		String venue = homeClub.getVenue();
		System.out.println("Venue: " + venue);

		if (assistant.addGame(new Game(id, name, homeTeamId, awayTeamId, date, venue)))
			System.out.println("Add new game successfully!");
		else
			System.out.println("Failed! This game already existed.");
	}

	private void menu_removeGame() {
		System.out.println("=======Remove a game========");
		System.out.print("ID: ");
		String id = sc.nextLine();
		if (assistant.removeGame(id))
			System.out.println("Remove a game successfully!");
		else
			System.out.println("Failed! This ID does not exist.");
	}

}
