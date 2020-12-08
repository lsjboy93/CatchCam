package com.ssafy.catchcam.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.catchcam.model.ItemResponse;
import com.ssafy.catchcam.model.RentalCancelValidResponse;
import com.ssafy.catchcam.model.RentalRequest;
import com.ssafy.catchcam.model.RentalResponse;
import com.ssafy.catchcam.model.ReviewListResponse;
import com.ssafy.catchcam.model.ReviewRequest;
import com.ssafy.catchcam.model.ReviewResponse;
import com.ssafy.catchcam.model.StoreResponse;

public interface RentalRepository {
	
	//대여 목록 조회
	public List<RentalResponse> getRentalList(@Param("userId") long userId) throws Exception;

	//대여하기
	public void insertRental(RentalRequest rental) throws Exception;

	//지점 목록 조회
	public List<StoreResponse> getStoreList() throws Exception;
	
	//단일 상품 조회
	public ItemResponse getItemInfo(@Param("itemId") long itemId) throws Exception;
	
	//상품 리뷰 조회(평점, 리뷰개수 등)
	public ReviewListResponse getItemReview(@Param("itemId") long itemId, @Param("userId") long userId) throws Exception;

	//리뷰 목록 조회
	public List<ReviewResponse> getReviewList(@Param("itemId") long itemId, @Param("userId") long userId, @Param("startPage") int startPage) throws Exception;
	
	//리뷰 등록
	public void insertReview(@Param("rental") ReviewRequest rental, @Param("userId") long userId) throws Exception;

	//리뷰 수정
	public void updateReview(ReviewRequest rental) throws Exception;
	
	//리뷰 삭제
	public void deleteReview(@Param("reviewId") long reviewId) throws Exception;
	
	//대여 예약 취소
	public void deleteRental(@Param("rentalId") long rentalId) throws Exception;
	
	//대여한 사람과 유저가 같은 지 확인
	public String checkRentalUser(@Param("rentalId") long rentalId, @Param("userId") long userId) throws Exception;
	
	//리뷰 작성한 사람과 유저가 같은 지 확인
	public String checkReviewUser(@Param("reviewId") long reviewId, @Param("userId") long userId) throws Exception;
	
	//대여 취소 전 유효성 검사 (대여날짜 이전인지, 대여자 본인인지, 리뷰가 이미 작성되어있는지 확인)
	public RentalCancelValidResponse checkRentalCancelValid(@Param("rentalId") long rentalId, @Param("userId") long userId) throws Exception;
	
	//최신 리뷰 조회
	public List<ReviewResponse> getRecentReviews() throws Exception;
}
