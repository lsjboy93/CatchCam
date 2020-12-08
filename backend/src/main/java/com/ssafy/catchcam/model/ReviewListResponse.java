package com.ssafy.catchcam.model;

import java.util.List;

public class ReviewListResponse {
	private long itemId;
	private Double avgRate;
	private int reviewCount;
	private String reviewYn;
	private List<ReviewResponse> reviews;

	public ReviewListResponse() {

	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public Double getAvgRate() {
		return avgRate;
	}

	public void setAvgRate(Double avgRate) {
		this.avgRate = avgRate;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public String getReviewYn() {
		return reviewYn;
	}

	public void setReviewYn(String reviewYn) {
		this.reviewYn = reviewYn;
	}

	public List<ReviewResponse> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewResponse> reviews) {
		this.reviews = reviews;
	}

	
	

}
