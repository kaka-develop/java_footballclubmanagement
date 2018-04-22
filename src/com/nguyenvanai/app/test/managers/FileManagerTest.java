package com.nguyenvanai.app.test.managers;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.nguyenvanai.app.managers.FileManager;

public class FileManagerTest {
	FileManager manager = FileManager.getInstance();

	String filePath = FileManager.FILE_PATH + "test.json";
	String text = "{message:'Hello World'}";

	// test write to file
	@Test
	public void testWriteToFile() {
		boolean expectedResult = true;
		boolean result = manager.saveJson(text, filePath);
		assertEquals(expectedResult, result);
	}

	// test read from file
	@Test
	public void readFromFile() throws IOException {
		String expectedResult = text;
		String result = manager.loadJson(filePath);
		assertEquals(expectedResult, result);
	}

}
