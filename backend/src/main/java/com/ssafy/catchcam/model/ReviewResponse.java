package com.ssafy.catchcam.model;

import java.util.Date;

public class ReviewResponse {
	private long reviewId;
	private long userId;
	private String userName;
	private String content;
	private Date createDate;
	private Date modifyDate;
	private String strCreateDate;
	private String strModifyDate;
	private Double starRate;
	private String writerYn;

	public ReviewResponse() {

	}

	public long getReviewId() {
		return reviewId;
	}

	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getWriterYn() {
		return writerYn;
	}

	public void setWriterYn(String writerYn) {
		this.writerYn = writerYn;
	}

	public String getStrCreateDate() {
		return strCreateDate;
	}

	public void setStrCreateDate(String strCreateDate) {
		this.strCreateDate = strCreateDate;
	}

	public String getStrModifyDate() {
		return strModifyDate;
	}

	public void setStrModifyDate(String strModifyDate) {
		this.strModifyDate = strModifyDate;
	}

	
	

}
