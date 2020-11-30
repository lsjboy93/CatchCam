 drop table item_list;
 drop table rental_list;
 drop table review_list;
 drop table user_list;

CREATE TABLE IF NOT EXISTS `user_list` (
	`user_id`	bigint	NOT NULL AUTO_INCREMENT COMMENT '사용자 테이블 pk',
	`email`	varchar(200)	NULL COMMENT '사용자 이메일 (로그인 아이디)',
	`password`	varchar(200)	NULL COMMENT '사용자 비밀번호',
	`user_name`	varchar(50)	NULL COMMENT '사용자 이름/닉네임',
	`zip_code`	varchar(50)	NULL COMMENT '우편번호',
	`addr1`	varchar(200)	NULL COMMENT '주소1',
	`addr2`	varchar(200)	NULL COMMENT '주소2',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `email_UNIQUE` (`email`)
) DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `item_list` (
	`item_id`	bigint	NOT NULL AUTO_INCREMENT COMMENT '제품 테이블 pk',
	`item_name`	varchar(200)	NULL COMMENT '제품명',
	`profile_img`	varchar(200)	NULL COMMENT '제품 사진',
	`description`	text	NULL COMMENT '제품 설명',
	`cost`	int	NULL COMMENT '제품 대여료',
    PRIMARY KEY (`item_id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `rental_list` (
	`rental_id`	bigint	NOT NULL AUTO_INCREMENT COMMENT '대여 테이블 pk',
	`item_id`	bigint	NOT NULL COMMENT '대여 제품 key',
	`user_id`	bigint	NOT NULL COMMENT '대여한 사용자 key',
	`store_id`	bigint	NOT NULL COMMENT '대여 지점 key',
	`rent_date`	datetime	NULL COMMENT '대여일시',
	`return_date`	datetime	NULL COMMENT '반납예정일시',
	`act_return_date`	datetime	NULL COMMENT '실제반납일시',
    PRIMARY KEY (`rental_id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `review_list` (
	`review_id`	bigint	NOT NULL AUTO_INCREMENT COMMENT '후기 테이블 pk',
	`rental_id`	bigint	NOT NULL COMMENT '대여 정보 ley',
    `user_id`	bigint	NOT NULL COMMENT '작성자 id',
	`content`	text	NULL COMMENT '후기 내용',
	`create_date`	datetime	NULL COMMENT '후기 작성일자',
	`modify_date`	datetime	NULL COMMENT '후기 수정일자',
	`star_rate`	double	NULL COMMENT '별점',
    PRIMARY KEY (`review_id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `store_list` (
	`store_id`	bigint	NOT NULL AUTO_INCREMENT COMMENT '지점 테이블 pk',
	`store_name`	varchar(200)	NULL COMMENT '지점명',
	`tel_no`	varchar(200)	NULL COMMENT '지점 연락처',
	`open_time`	int	NULL COMMENT '운영시작시간',
	`close_time`	int	NULL COMMENT '운영종료시간',
	`latitude`	double	NULL COMMENT '위도',
	`longitude`	double	NULL COMMENT '경도',
	`address`	varchar(200)	NULL COMMENT '지점 주소',
    PRIMARY KEY (`store_id`)
) DEFAULT CHARSET=utf8;




