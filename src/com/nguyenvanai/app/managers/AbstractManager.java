package com.nguyenvanai.app.managers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.nguyenvanai.app.models.AbstractEntity;

public abstract class AbstractManager {

	private Map<String, AbstractEntity> map;

	protected AbstractManager() {
		map = new HashMap<String, AbstractEntity>();

	}

	public boolean clear() {
		map.clear();
		return map.size() == 0;
	}

	// get a list of entities
	public List<AbstractEntity> all() {
		return new ArrayList<>(map.values());
	}

	// get an entity by ID
	public AbstractEntity get(String id) {
		if (isExisted(id)) {
			return map.get(id);
		}
		return null;
	}

	// get Map
	protected Map<String, AbstractEntity> getMap() {
		return map;
	}

	// check existing entity in map
	public boolean isExisted(String id) {
		return map.containsKey(id);
	}

	// check count of entities in map
	public int count() {
		return map.size();
	}

	// add an entity to map
	public boolean add(AbstractEntity item) {

		if (isExisted(item.getId())) {
			return false;
		} else {
			if (validateID(item.getId())) {
				map.put(item.getId(), item);
				return true;
			}
		}
		return false;
	}

	// update an entity in map
	public boolean update(String id, AbstractEntity item) {
		if (!isExisted(id))
			return false;
		else {
			map.replace(id, item);
		}
		return true;
	}

	// delete an entity from map
	public boolean delete(String id) {
		if (!isExisted(id))
			return false;
		else
			map.remove(id);
		return true;
	}

	// get json file name
	public abstract String getFileName();

	// get type of collection<?>
	abstract Type getListType();

	// validate id of entity
	abstract boolean validateID(String id);

	// save data to json file
	public boolean save() {
		Gson gson = new Gson();
		String json = gson.toJson(map.values());
		return FileManager.getInstance().saveJson(json, FileManager.FILE_PATH + getFileName());
	}

	// load data from json file
	public boolean loadData() throws Exception {
		Gson gson = new Gson();
		String json = FileManager.getInstance().loadJson(FileManager.FILE_PATH + getFileName());
		Collection<AbstractEntity> list;
		list = gson.fromJson(json, getListType());
		map.clear();
		list.forEach(item -> {
			map.put(item.getId(), item);
		});

		return list != null;
	}

	// sort data by Id
	public List<AbstractEntity> sortByID() {
		TreeMap<String, AbstractEntity> sorted = new TreeMap<>(map);
		return new ArrayList<>(sorted.values());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("");
		map.values().forEach(item -> {
			builder.append(item.toString());
			builder.append("\n");
		});
		return builder.toString();
	}

}
