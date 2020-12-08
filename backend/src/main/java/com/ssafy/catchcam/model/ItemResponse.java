package com.ssafy.catchcam.model;

import java.util.Date;

public class ItemResponse {
	private long itemId;
	private String itemName;
	private String profileImg;
	private String description;
	private int cost;
	

	public ItemResponse() {

	}


	public long getItemId() {
		return itemId;
	}


	public void setItemId(long itemId) {
		this.itemId = itemId;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getProfileImg() {
		return profileImg;
	}


	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getCost() {
		return cost;
	}


	public void setCost(int cost) {
		this.cost = cost;
	}



	
	

}
