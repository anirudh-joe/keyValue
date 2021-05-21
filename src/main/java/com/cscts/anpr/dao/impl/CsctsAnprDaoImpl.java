package com.cscts.anpr.dao.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cscts.anpr.dao.CsctsAnprDao;
import com.cscts.anpr.model.AccelDataKV;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class CsctsAnprDaoImpl implements CsctsAnprDao {
	private static Logger logger = LoggerFactory.getLogger(CsctsAnprDaoImpl.class);

	@Override
	public String readkeyValue(String key) {
		String file = "src/test/resource/fileTest.json";
		Gson gson = new Gson();
		String returnValue = null;
		BufferedReader reader;
		List<AccelDataKV> kvs = new ArrayList<AccelDataKV>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String currentLine = reader.readLine();
			if (currentLine == null) {
				reader.close();
				return null;
			}
			kvs = gson.fromJson(currentLine, new TypeToken<List<AccelDataKV>>() {
			}.getType());
			
			for (AccelDataKV accelDataKV : kvs) {
				if (accelDataKV.getKey().equalsIgnoreCase(key)) {
					returnValue = accelDataKV.getValue();
				}
			}
			reader.close();
		} catch (IOException e) {
			logger.error("Unable to Fetch Object {}", e.getMessage());
		}
		logger.info("Found The Object", returnValue);
		return returnValue;
	}

	@Override
	public boolean updateKeyValue(String key, AccelDataKV value) {
		String file = "src/test/resource/fileTest.json";
		Gson gson = new Gson();
		List<AccelDataKV> kvs = new ArrayList<AccelDataKV>();
		BufferedReader reader;
		boolean output = true;
		try {
			reader = new BufferedReader(new FileReader(file));
			String currentLine = reader.readLine();

			if (currentLine == null || currentLine.equalsIgnoreCase("[]")) {
				if (value != null) {
					kvs.add(value);
				}else {
					output = false;	
				}
			} else {
				kvs = gson.fromJson(currentLine, new TypeToken<List<AccelDataKV>>() {
				}.getType());
				if (kvs.isEmpty()) {
					output = false;
				}
				for (AccelDataKV accelDataKV : kvs) {
					if (accelDataKV.getKey().equalsIgnoreCase(key)) {
						kvs.remove(accelDataKV);
						break;
					}
				}
				if (value != null) {
					kvs.add(value);
				}
			}
			FileWriter fw = new FileWriter(file);
			gson.toJson(kvs, fw);
			reader.close();
			fw.close();
			
		} catch (IOException e) {
			logger.error("Unable to Fetch Object {}", e.getMessage());
			output = false;
			return output;
		}
		return output;
	}

}