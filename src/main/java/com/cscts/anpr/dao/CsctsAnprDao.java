package com.cscts.anpr.dao;

import com.cscts.anpr.model.AccelDataKV;

public interface CsctsAnprDao {

	String readkeyValue(String key);
	
	boolean updateKeyValue(String key, AccelDataKV value);
}
