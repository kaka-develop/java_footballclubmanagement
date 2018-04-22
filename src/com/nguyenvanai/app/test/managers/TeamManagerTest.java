package com.nguyenvanai.app.test.managers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.nguyenvanai.app.managers.TeamManager;
import com.nguyenvanai.app.models.Team;

public class TeamManagerTest {
	TeamManager manager = TeamManager.getInstance();

	@Before
	public void setup() {
		manager.add(new Team("ZIM002MAI", "Main Chelsea 2", "ZIM000CHE"));
		manager.add(new Team("ZIM000MAI", "Main Chelsea", "ZIM000CHE"));
		manager.add(new Team("ZIM001MAI", "Main Chelsea 1", "ZIM000CHE"));
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
		boolean result = manager.isExisted("ZIM000MAI");
		assertEquals(expectedResult, result);
	}
	@Test
	public void testValidateID() {
		boolean expectedResult = true;
		boolean result = manager.validateID("ZIM000MAI");
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
		String id = manager.generateIdFromName("Main Chelsea");
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
	public void testAddTeam() {
		Team team = new Team("ZIM003MAI", "Main Chelsea", "ZIM000CHE");
		boolean expectedResult = true;
		boolean result = manager.add(team);
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.add(team);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testDeleteTeam() {
		String id = "ZIM000MAI";
		boolean expectedResult = true;
		boolean result = manager.delete(id);
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.delete("sasa");
		assertEquals(expectedResult, result);
	}

	@Test
	public void testUpdateTeam() {
		Team team = new Team("ZIM000MAI", "Main Chelsea 2", "ZIM000CHE");
		boolean expectedResult = true;
		boolean result = manager.update(team.getId(), team);
		assertEquals(expectedResult, result);

		expectedResult = false;
		result = manager.update("ZIM001LIV", team);
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void testGetFileName() {
		String expectedResult = "teams.json";
		String result = manager.getFileName();
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void testSortByID() {
		String expectedResult = "ZIM000MAI";
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
