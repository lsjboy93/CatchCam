package com.ssafy.catchcam.model;

import java.util.Date;

public class StoreResponse {
	private long storeId;
	private String storeName;
	private String telNo;
	private int openTime;
	private int closeTime;
	private Double latitude;
	private Double longitude;
	private String address;
	

	public StoreResponse() {

	}


	public long getStoreId() {
		return storeId;
	}


	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}


	public String getStoreName() {
		return storeName;
	}


	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}


	public String getTelNo() {
		return telNo;
	}


	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}


	public int getOpenTime() {
		return openTime;
	}


	public void setOpenTime(int openTime) {
		this.openTime = openTime;
	}


	public int getCloseTime() {
		return closeTime;
	}


	public void setCloseTime(int closeTime) {
		this.closeTime = closeTime;
	}


	public Double getLatitude() {
		return latitude;
	}


	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public Double getLongitude() {
		return longitude;
	}


	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	
	

}
