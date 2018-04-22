package com.nguyenvanai.app.managers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileManager {

	private static final FileManager instance = new FileManager();

	public static FileManager getInstance() {
		return instance;
	}

	private FileManager() {
	}

	public static void main(String[] args) throws IOException {
		FileManager manager = new FileManager();
		manager.writeTextToFile(FileManager.CLUB_FILE, "Hello world");
		manager.readTextFromFile(FileManager.CLUB_FILE);
	}

	public final static String FILE_PATH = "./src/com/nguyenvanai/app/data/";
	public final static String CLUB_FILE = FILE_PATH + "club.json";

	private boolean writeTextToFile(String filePath, String text) {
		Path path = Paths.get(filePath);
		List<String> ass = Arrays.asList(text);
		try {
			Files.write(path, ass, Charset.forName("UTF-8"));
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private String readTextFromFile(String filePath) throws IOException {
		String text = "";
		Path path = Paths.get(filePath);

		text = Files.readAllLines(path).get(0);

		return text;
	}

	public boolean saveJson(String json, String filePath) {
		return writeTextToFile(filePath, json);
	}

	public String loadJson(String filePath) throws IOException {
		return readTextFromFile(filePath);
	}

}
