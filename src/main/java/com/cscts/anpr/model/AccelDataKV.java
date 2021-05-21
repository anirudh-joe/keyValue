package com.cscts.anpr.model;

public class AccelDataKV {
	private String key;
	private String value;
	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "AccelDataKV [key=" + key + ", value=" + value + "]";
	}

		
}
