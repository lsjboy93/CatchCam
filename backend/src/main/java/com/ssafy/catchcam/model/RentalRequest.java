package com.ssafy.catchcam.model;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

public class RentalRequest {
	private long rentalId;
	@ApiModelProperty(required = true)
	private long itemId;
	@ApiModelProperty(required = true)
	private long userId;
	@ApiModelProperty(required = true)
	private long storeId;
	private Date rentDate;
	private Date returnDate;
	private Date actReturnDate;


	public RentalRequest() {
	}

	public RentalRequest(long itemId, long userId, long storeId, Date rentDate, Date returnDate) {
		this.itemId = itemId;
		this.userId = userId;
		this.storeId = storeId;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
	}

	public RentalRequest(long rentalId, long itemId, long userId, long storeId, Date rentDate, Date returnDate, Date actReturnDate) {
		this.rentalId = rentalId;
		this.itemId = itemId;
		this.userId = userId;
		this.storeId = storeId;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
		this.actReturnDate = actReturnDate;
	}
	
	
	public Date getActReturnDate() {
		return actReturnDate;
	}

	public void setActReturnDate(Date actReturnDate) {
		this.actReturnDate = actReturnDate;
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

}
