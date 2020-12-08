package com.ssafy.catchcam.model;

public class RentalCancelValidResponse {
	private String validDate;
	private String isMine;
	private String existReview;
	
	public RentalCancelValidResponse() {

	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getIsMine() {
		return isMine;
	}

	public void setIsMine(String isMine) {
		this.isMine = isMine;
	}

	public String getExistReview() {
		return existReview;
	}

	public void setExistReview(String existReview) {
		this.existReview = existReview;
	}

}
