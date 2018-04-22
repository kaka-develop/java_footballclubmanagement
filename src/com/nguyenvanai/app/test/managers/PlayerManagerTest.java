package com.nguyenvanai.app.test.managers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.nguyenvanai.app.managers.PlayerManager;
import com.nguyenvanai.app.models.Player;

public class PlayerManagerTest {
	PlayerManager manager = PlayerManager.getInstance();

	@Before
	public void setup() {
		manager.add(new Player("NHN0001", "Nguyen Hai Nam", 21, "ZIM001MAI"));
		manager.add(new Player("NDA0001", "Nguyen Duc Anh", 21, "ZIM001MAI"));
		manager.add(new Player("NXT0001", "Nguyen Xuan Tien", 21, "ZIM001MAI"));
	}

	@Test
	public void clear() {
		boolean expectedResult = true;
		boolean result = manager.clear();
		assertEquals(expectedResult, result);
	}

	@Test
	public void testIsExisted() {
		boolean expectedResult = true;
		boolean result = manager.isExisted("NHN0001");
		assertEquals(expectedResult, result);
	}

	@Test
	public void testValidateName() {
		boolean expectedResult = true;
		boolean result = manager.validateName("Nguyen Hai Nam");
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.validateName("Nguyen Nam");
		assertEquals(expectedResult, result);
	}

	@Test
	public void testValidateID() {
		boolean expectedResult = true;
		boolean result = manager.validateID("NHN0001");
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.validateID("sasada");
		assertEquals(expectedResult, result);
		result = manager.validateID("ZIM0000C");
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGenerateIDFromName() {
		boolean expectedResult = true;
		String id = manager.generateIdFromName("Nguyen Hai Nam");
		boolean result = manager.validateID(id);
		assertEquals(expectedResult, result);

		expectedResult = false;
		id = manager.generateIdFromName("Cs");
		result = manager.validateID(id);
		assertEquals(expectedResult, result);
		id = manager.generateIdFromName("C sa");
		result = manager.validateID(id);
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void testChangePlayerStatus() {
		boolean expectedResult = true;
		Player player = new Player("NHN0003", "Nguyen Hai Nam", 21, "ZIM001MAI");
		manager.add(player);
		boolean result = manager.changePlayerStatus("NHN0003", 2);
		assertEquals(expectedResult, result);
		
		expectedResult = false;
		result = manager.changePlayerStatus("NHNsas", 2);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testAddPlayer() {
		Player player = new Player("NHN0002", "Nguyen Hai Nam", 21, "ZIM001MAI");
		boolean expectedResult = true;
		boolean result = manager.add(player);
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.add(player);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testDeletePlayer() {
		String id = "NHN0001";
		boolean expectedResult = true;
		boolean result = manager.delete(id);
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.delete("sasa");
		assertEquals(expectedResult, result);
	}

	@Test
	public void testUpdatePlayer() {
		Player player = new Player("NHN0001", "Nguyen Xuan Huy", 21, "ZIM001MAI");
		boolean expectedResult = true;
		boolean result = manager.update(player.getId(), player);
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.update("ZIM001LIV", player);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetFileName() {
		String expectedResult = "players.json";
		String result = manager.getFileName();
		assertEquals(expectedResult, result);
	}

	@Test
	public void testSortByID() {
		String expectedResult = "NDA0001";
		String result = manager.sortByID().get(0).getId();
		assertEquals(expectedResult, result);
	}

	@Test
	public void testLoadData() throws Exception {
		boolean expectedResult = true;
		boolean result = manager.loadData();
		assertEquals(expectedResult, result);
	}

}
