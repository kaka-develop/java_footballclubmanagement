package com.nguyenvanai.app.test.managers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.nguyenvanai.app.managers.GameManager;
import com.nguyenvanai.app.models.Game;

public class GameManagerTest {
	GameManager manager = GameManager.getInstance();

	@Before
	public void setup() {
		
		manager.add(new Game("001CHEv002LIV10202016", "Chelsea vs Liverpool", "ZIM001CHE", "ZIM002LIV"));
		manager.add(new Game("002CHEv002LIV10202016", "Chelsea vs Liverpool", "ZIM002CHE", "ZIM002LIV"));
		manager.add(new Game("003CHEv002LIV10202016", "Chelsea vs Liverpool", "ZIM003CHE", "ZIM002LIV"));
	}

	@Test
	public void clear() {
		boolean expectedResult = true;
		boolean result = manager.clear();
		assertEquals(expectedResult, result);
	}

	@Test 
	public void testIsExisted(){
		boolean expectedResult = true;
		boolean result = manager.isExisted("001CHEv002LIV10202016");
		assertEquals(expectedResult, result);
	}
	@Test
	public void testValidateID() {
		boolean expectedResult = true;
		boolean result = manager.validateID("001CHEv002LIV10202016");
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.validateID("sasada");
		assertEquals(expectedResult, result);
		result = manager.validateID("ZIM0000C");
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGenerateIDFromTeams() {
		boolean expectedResult = true;
		String id = manager.generateIDFromTeams("ZIM001CHE", "ZIM002LIV", "10212016");
		boolean result = manager.validateID(id);
		assertEquals(expectedResult, result);

		expectedResult = false;
		id = manager.generateIDFromTeams("sasa", "ZIM002LIV", "s");
		result = manager.validateID(id);
		assertEquals(expectedResult, result);
		id = manager.generateIDFromTeams("ZIM001CHE", "ssa", "10212016");
		result = manager.validateID(id);
		assertEquals(expectedResult, result);
	}
	
	@Test 
	public void testGenerateNameFromTeams(){
		String expectedResult = "Chelsea vs Liverpool";
		String result = manager.generateNameFromTeams("Chelsea", "Liverpool");
		assertEquals(expectedResult, result);
		
		expectedResult = "Chelsea vs Manchester";
		result = manager.generateNameFromTeams("Chelsea", "Manchester");
		assertEquals(expectedResult, result);
	}

	@Test
	public void testAddGame() {
		Game game = new Game("004CHEv002LIV10202016", "Chelsea vs Liverpool", "ZIM003CHE", "ZIM002LIV");
		boolean expectedResult = true;
		boolean result = manager.add(game);
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.add(game);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testDeleteGame() {
		String id = "001CHEv002LIV10202016";
		boolean expectedResult = true;
		boolean result = manager.delete(id);
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.delete("sasa");
		assertEquals(expectedResult, result);
	}

	@Test
	public void testUpdateGame() {
		Game game = new Game("003CHEv002LIV10202016", "Chelsea vs Liverpool", "ZIM003CHE", "ZIM002LIV");
		boolean expectedResult = true;
		boolean result = manager.update(game.getId(), game);
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.update("ZIM001LIV", game);
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void testGetFileName() {
		String expectedResult = "games.json";
		String result = manager.getFileName();
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void testSortByID() {
		String expectedResult = "001CHEv002LIV10202016";
		String result =manager.sortByID().get(0).getId();
		assertEquals(expectedResult, result);
	}
	
	
	@Test
	public void testLoadData() throws Exception {
		boolean expectedResult = true;
		boolean result = manager.loadData();
		assertEquals(expectedResult, result);
	}
	

}
