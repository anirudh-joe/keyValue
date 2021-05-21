package com.cscts.anpr.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cscts.anpr.model.AccelDataKV;
import com.cscts.anpr.service.AnprDeviceService;

@RestController
@RequestMapping("/api/")
public class AnprDeviceController {

	private static Logger logger = LoggerFactory.getLogger(AnprDeviceController.class);

	@Autowired
	private AnprDeviceService service;

	@PostMapping(value = "/createKV", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createKV(@RequestBody AccelDataKV cuft) throws Exception {
//		logger.info("Incoming Message from camera {} and device id {} ", a,b);
		boolean validPacket;
		if (service.readKVFS(cuft.getKey()) == null) {
			validPacket = service.updateKVFS(cuft.getKey(), cuft);
		} else {
			validPacket = false;
		}

		Map<String, String> body = new HashMap<>();
		if (validPacket) {
			body.put("message", "Store Successful");
		} else {
			body.put("message", "Store Failed Key already exists call updateKV to Update");
		}
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("response", body);
		return ResponseEntity.ok(responseBody);
	}

	@PostMapping(value = "/updateKV", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateKV(@RequestBody AccelDataKV cuft) throws Exception {
//		logger.info("Incoming Message from camera {} and device id {} ", a,b);

		boolean validPacket = service.updateKVFS(cuft.getKey(), cuft);
		logger.info("{} is missing", validPacket);
		Map<String, String> body = new HashMap<>();
		if (validPacket) {
			body.put("message", "Update Successful");
		} else {
			body.put("message", "Update Failed");
		}
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("response", body);
		return ResponseEntity.ok(responseBody);
	}

	@PostMapping(value = "/deleteKV", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteKV(@RequestBody AccelDataKV deleteInput) throws Exception {
//		logger.info("Incoming Message from camera {} and device id {} ", a,b);
		boolean validPacket = service.updateKVFS(deleteInput.getKey(), null);
		logger.info("{} is missing", validPacket);
		Map<String, String> body = new HashMap<>();
		if (validPacket) {
			body.put("message", "Delete Successful ");
		} else {
			body.put("message", "Delete Failed ");
		}
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("response", body);
		return ResponseEntity.ok(responseBody);
	}

	@GetMapping(value = "/readKV", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> readKV(@RequestParam(name = "key") String key) throws Exception {
		String out = service.readKVFS(key);
		AccelDataKV kv = new AccelDataKV();
		kv.setKey(key);
		if (out  != null) {
			kv.setValue(out);
			return ResponseEntity.ok(kv);
		} else {
			kv.setValue("Not Found");
			return ResponseEntity.ok(kv);
		}
		
	}
}