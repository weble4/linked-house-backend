create table if not exists customer (
	customer_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY key,
    customer_email VARCHAR(50) NOT NULL,
    customer_pw VARCHAR(100) NOT NULL,
    delete_request VARCHAR(30) NOT NULL,
    role varchar(30) not null,
    auth_state varchar(30) not null,	
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP not NULL
);

create table if not exists reservation (
	reservation_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    rental_id BIGINT NOT NULL,
	customer_id BIGINT NOT NULL,
    # 체크인 날짜 인지 시각 인지 확인 필요
    reservation_num int not null,
    checkin_date DATE not null,
    checkout_date DATE not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP not NULL
);

create table if not exists house (
	rental_id BIGINT AUTO_INCREMENT not null primary key,
    host_id BIGINT not null,
    title varchar(200) not null,
    description varchar(2000) not null,
    max_capacity TINYINT not null,
    min_capacity TINYINT not null,
    price int not null,
    location varchar(200) not null,
    image varchar(200),
    auto_reservation varchar(10) not null,
    room int not null,
    bed int not null,
    bath_room int not null
);

create table if not exists payment (
	payment_id BIGINT AUTO_INCREMENT not null,
    reservation_id BIGINT not null,
    rental_id bigint not null,
    price int not null DEFAULT 
		((select price from info_house where house.rental_id = rental_id) 
		* (select reservation_num from reservation where reservation.reservation_id = reservation_id)),
    request_day TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

create table if not exists report (
	report_id BIGINT AUTO_INCREMENT NOT NULL,
    feedback_host_id BIGINT,
    feedback_customer_id BIGINT,
    reported_customer BIGINT,
    reporter BIGINT NOT NULL,
    report_type VARCHAR(20),
    content VARCHAR(1000)
);

create table if not exists feedback_customer (
	feedback_customer_id BIGINT AUTO_INCREMENT NOT NULL,
    customer_id BIGINT NOT NULL,
    host_id BIGINT NOT NULL,
    title VARCHAR(200),
    content VARCHAR(1000),
    score_clean INT NOT NULL DEFAULT 0,
    score_communication INT NOT NULL DEFAULT 0,
    score_satisfaction INT NOT NULL DEFAULT 0,
    total_score INT not null 
		default ((score_clean + score_communication + score_satisfaction) / 3),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP not NULL
);

create table if not exists feedback_host (
	feedback_host_id BIGINT AUTO_INCREMENT NOT NULL,
    customer_id BIGINT NOT NULL,
    rental_id BIGINT NOT NULL,
    title VARCHAR(200),
    content VARCHAR(1000),
    attitude INT NOT NULL DEFAULT 0,
    disrepair INT NOT NULL DEFAULT 0,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP not NULL
);

create table if not exists host (
	host_id bigint AUTO_INCREMENT not null,
    customer_id bigint not null,
    approve_state varchar(20) not null,
    lock_state varchar(20) not null
);

create table if not EXISTS message (
	message_id BIGINT not null AUTO_INCREMENT,
    message_room BIGINT not null ,
    content VARCHAR(1000) not null,
    receive_customer_id BIGINT not null,
    send_customer_id BIGINT not null,
    read_check varchar(20) not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP not NULL
);

create table if not EXISTS customer_profile (
	profile_id BIGINT not null AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    nickname VARCHAR(20),
    gender VARCHAR(1) NOT NULL,
    birth_date DATE NOT NULL,
    phone_num INT not null,
    public_at VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP not NULL
);


create table if not exists delete_customer (
	customer_id BIGINT NOT NULL,
    request_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
    # TRIGGER 로 30일 추가해야함
);


# 동적 테이블
DELIMITER $$
CREATE PROCEDURE `weble`.`create_delete_customer` (IN customer_id BIGINT)
BEGIN
    SET @sql = CONCAT('CREATE TABLE ', customer_id, '_delete_customer (request_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)');
    PREPARE s1 FROM @sql;
    EXECUTE s1;
    DEALLOCATE PREPARE s1;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `weble`.`create_bookmark` (IN customer_id BIGINT)
BEGIN
    SET @sql = CONCAT('CREATE TABLE ', customer_id, '_bookmark (rental_id BIGINT NOT NULL)');
    PREPARE s1 FROM @sql;
    EXECUTE s1;
    DEALLOCATE PREPARE s1;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `weble`.`create_banned_customer` (IN customer_id BIGINT)
BEGIN
    SET @sql = CONCAT('CREATE TABLE ', customer_id, '_banned_customer (locked_date TIMESTAMP NOT NULL, unlocked_date TIMESTAMP NOT NULL, locked_at VARCHAR(30) NOT NULL)');
    PREPARE s1 FROM @sql;
    EXECUTE s1;
    DEALLOCATE PREPARE s1;
END$$
DELIMITER ;

create table if not exists notification (
	notification_id BIGINT AUTO_INCREMENT not null,
    customer_id BIGINT not null,
    notification_type varchar(20),
    notification_content varchar(200)
);

create table if not exists cancel_reservation (
	cancel_id BIGINT AUTO_INCREMENT not null,
    reservation_id BIGINT not null,
    reservation_num int,
    checkin_date DATE,
    checkout_date DATE
);