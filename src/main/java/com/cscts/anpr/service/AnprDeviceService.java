package com.cscts.anpr.service;

import org.springframework.stereotype.Service;

import com.cscts.anpr.model.AccelDataKV;


@Service
public interface AnprDeviceService {

	boolean updateKVFS(String key, AccelDataKV value);
	String readKVFS(String key);
}
