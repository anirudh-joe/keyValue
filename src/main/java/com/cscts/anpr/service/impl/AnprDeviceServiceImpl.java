package com.cscts.anpr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cscts.anpr.dao.CsctsAnprDao;
import com.cscts.anpr.model.AccelDataKV;
import com.cscts.anpr.service.AnprDeviceService;

@Component
public class AnprDeviceServiceImpl implements AnprDeviceService {

	private static Logger logger = LoggerFactory.getLogger(AnprDeviceServiceImpl.class);

	@Autowired
	public CsctsAnprDao dao;

	@Override
	public String readKVFS(String key) {
		logger.info("Inside Service. Read the value for given key {}", key);
		return dao.readkeyValue(key);
		
	}

	@Override
	public boolean updateKVFS(String key, AccelDataKV value) {
		if (value == null) {
			logger.info("Inside Service. Delete Request Packet for the key {}", key);
		} else {
			logger.info("Inside Service. Save Packet with key value being {} {}", key, value.toString());
		}
		boolean val = dao.updateKeyValue(key, value);
		return val;
	}

}
