package com.ssafy.catchcam.model;

import java.util.Date;

public class RentalResponse {
	private long rentalId;
	private long itemId;
	private long userId;
	private long storeId;
	private Date rentDate;
	private Date returnDate;
	private Date actReturnDate;
	private String strRentDate;
	private String strReturnDate;
	private String strActReturnDate;
	private String status;
	private String itemName;
	private String profileImg;
	private int cost;
	private String storeName;
	private String telNo;
	private int openTime;
	private int closeTime;
	private String address;
	private String reviewYn;

	
	public RentalResponse() {

	}

	public long getRentalId() {
		return rentalId;
	}

	public void setRentalId(long rentalId) {
		this.rentalId = rentalId;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Date getActReturnDate() {
		return actReturnDate;
	}

	public void setActReturnDate(Date actReturnDate) {
		this.actReturnDate = actReturnDate;
	}

	public String getStrRentDate() {
		return strRentDate;
	}

	public void setStrRentDate(String strRentDate) {
		this.strRentDate = strRentDate;
	}

	public String getStrReturnDate() {
		return strReturnDate;
	}

	public void setStrReturnDate(String strReturnDate) {
		this.strReturnDate = strReturnDate;
	}

	public String getStrActReturnDate() {
		return strActReturnDate;
	}

	public void setStrActReturnDate(String strActReturnDate) {
		this.strActReturnDate = strActReturnDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReviewYn() {
		return reviewYn;
	}

	public void setReviewYn(String reviewYn) {
		this.reviewYn = reviewYn;
	}
	
	

}
