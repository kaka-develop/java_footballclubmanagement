package com.nguyenvanai.app.test.managers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.nguyenvanai.app.managers.ClubManager;
import com.nguyenvanai.app.models.Club;

public class ClubManagerTest {
	ClubManager manager = ClubManager.getInstance();

	@Before
	public void setup() {
		manager.add(new Club("ZIM002CHE", "Chelsea FC 2", "Stamford Bridge 1"));
		manager.add(new Club("ZIM000CHE", "Chelsea FC", "Stamford Bridge"));
		manager.add(new Club("ZIM001CHE", "Chelsea FC 1", "Stamford Bridge 2"));
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
		boolean result = manager.isExisted("ZIM000CHE");
		assertEquals(expectedResult, result);
	}
	@Test
	public void testValidateID() {
		boolean expectedResult = true;
		boolean result = manager.validateID("ZIM000CHE");
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
		String id = manager.generateIdFromName("Chelsea FC");
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
	public void testAddClub() {
		Club club = new Club("ZIM003CHE", "Chelsea FC", "Stamford Bridge");
		boolean expectedResult = true;
		boolean result = manager.add(club);
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.add(club);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testDeleteClub() {
		String id = "ZIM000CHE";
		boolean expectedResult = true;
		boolean result = manager.delete(id);
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.delete("sasa");
		assertEquals(expectedResult, result);
	}

	@Test
	public void testUpdateClub() {
		Club club = new Club("ZIM000CHE", "Chelsea FC 2", "Stamford Bridge 2");
		boolean expectedResult = true;
		boolean result = manager.update(club.getId(), club);
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.update("ZIM001LIV", club);
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void testGetFileName() {
		String expectedResult = "clubs.json";
		String result = manager.getFileName();
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void testSortByID() {
		String expectedResult = "ZIM000CHE";
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
