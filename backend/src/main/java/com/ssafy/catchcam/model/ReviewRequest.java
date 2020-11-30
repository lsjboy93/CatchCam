package com.ssafy.catchcam.model;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

public class ReviewRequest {
	private long reviewId;
	@ApiModelProperty(required = true)
	private long rentalId;
	@ApiModelProperty(required = true)
	private String content;
	private Date createDate;
	private Date modifyDate;
	private Double starRate;


	public ReviewRequest() {
	}


	public long getReviewId() {
		return reviewId;
	}


	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}


	public long getRentalId() {
		return rentalId;
	}


	public void setRentalId(long rentalId) {
		this.rentalId = rentalId;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getModifyDate() {
		return modifyDate;
	}


	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}


	public Double getStarRate() {
		return starRate;
	}


	public void setStarRate(Double starRate) {
		this.starRate = starRate;
	}


}
