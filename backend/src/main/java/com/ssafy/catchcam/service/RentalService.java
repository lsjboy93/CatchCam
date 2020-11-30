package com.ssafy.catchcam.service;

import java.util.List;

import com.ssafy.catchcam.model.ItemResponse;
import com.ssafy.catchcam.model.RentalRequest;
import com.ssafy.catchcam.model.RentalResponse;
import com.ssafy.catchcam.model.ReviewListResponse;
import com.ssafy.catchcam.model.ReviewRequest;
import com.ssafy.catchcam.model.ReviewResponse;
import com.ssafy.catchcam.model.StoreResponse;

public interface RentalService {

	//대여 목록 조회
	public List<RentalResponse> getRentalList() throws Exception;
	
	//대여하기
	public void insertRental(RentalRequest rental) throws Exception;

	//지점 목록 조회
	public List<StoreResponse> getStoreList() throws Exception;

	//단일 상품 조회
	public ItemResponse getItemInfo(long itemId) throws Exception;

	//리뷰 목록 조회
	public ReviewListResponse getReviewList(long itemId, long userId, int page) throws Exception;
	
	//리뷰 등록
	public void insertReview(ReviewRequest rental) throws Exception;
	
	//리뷰 수정
	public void updateReview(ReviewRequest rental) throws Exception;
	
	//리뷰 삭제
	public void deleteReview(long reviewId) throws Exception;
	
	//대여 예약 취소
	public void deleteRental(long rentalId) throws Exception;
	
	//최신 리뷰 조회
	public List<ReviewResponse> getRecentReviews() throws Exception;
}
