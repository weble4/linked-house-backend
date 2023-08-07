INSERT INTO customer (customer_email, customer_pw, delete_request, role, auth_state)
VALUES
    ('user1@example.com', 'password1', 'no', 'host', 'active'),
    ('user2@example.com', 'password2', 'no', 'host', 'active'),
    ('user3@example.com', 'password3', 'no', 'customer', 'active'),
    ('user4@example.com', 'password4', 'no', 'customer', 'active'),
    ('user5@example.com', 'password5', 'no', 'customer', 'active');

# birthdate는 DATE 이므로 YYYYMMDD 형식 입력 가능
INSERT INTO customer_profile (customer_id, nickname, gender, birth_date, phone_num, public_at)
VALUES
    (1, '뭐시기호텔', 'M', '1990-05-15', 01001010101, 'public'),
    (2, '저시기민박', 'F', '1985-08-22', 01012345678, 'public'),
    (3, '자연사랑자', 'M', '1992-11-10', 01089474902, 'public'),
    (4, '도심탐험가', 'F', '1988-02-28', 01023840532, 'public'),
    (5, '문화체험가', 'M', '1995-04-17', 01023645565, 'private');

# 1, 2번 유저는 HOST 권한
INSERT INTO host (customer_id, approve_state, lock_state)
VALUES
    (1, 'YES', 'NO'),
    (2, 'YES', 'NO');

INSERT INTO house (host_id, title, description, max_capacity, min_capacity, price, location, image, auto_reservation, room, bed, bath_room)
VALUES
    (1, '편안한 산장', '매력적인 산장 휴양지...', 4, 2, 150000, '파주, 경기', 'image1.jpg', 'yes', 3, 2, 2),
    (2, '해변가 파라다이스', '아름다운 바다가 내려다보이는...', 6, 4, 250000, '해운대, 부산', NULL, 'no', 4, 3, 2);

INSERT INTO reservation (rental_id, customer_id, reservation_num, checkin_date, checkout_date)
VALUES
    (1, 3, 2, '2023-08-14', '2023-08-20'),
    (2, 4, 4, '2023-08-16', '2023-08-22'),
    (2, 5, 6, '2023-08-23', '2023-08-25');

INSERT INTO feedback_host (customer_id, host_id, title, content, attitude, disrepair)
VALUES
    (3, 1, '불편했습니다', '게스트의 태도가 좋지 않았습니다.', 2, 1),
    (4, 2, '만족', '좋은 게스트였습니다..', 5, 5),
    (5, 2, '보통입니다', '평범한 게스트였습니다.', 3, 3);

INSERT INTO feedback_customer (customer_id, rental_id, title, content, score_clean, score_communication, score_satisfaction)
VALUES
    (3, 1, '별로에요', '기대에 못 미치는 청결도와 서비스였습니다.', 1, 1, 1),
    (4, 2, '좋았어요', '숙소 상태와 서비스가 좋았습니다.', 5, 5, 5),
    (5, 2, '괜찮아요', '보통 수준의 청결도와 서비스였습니다.', 3, 3, 3);

INSERT INTO report (feedback_host_id, feedback_customer_id, reported_customer, reporter, report_type, content)
VALUES
    (1, 1, 1, 3, 'clean', '숙소가 너무 더러워요.'),
    (3, 3, 5, 2, 'other', '그냥 신고해봅니다.');
    
INSERT INTO message (message_room, content, receive_customer_id, send_customer_id, read_check)
VALUES
    (1, '안녕하세요!', 1, 3, 'read'),
    (1, '반갑습니다.', 3, 1, 'not read'),
    (1, '숙박인원 추가 가능할까요?', 1, 3, 'not read'),
    (1, '가능합니다', 3, 1, 'read'),
    (2, '예약 변경 가능한가요?', 2, 4, 'not read');

INSERT INTO notification (customer_id, notification_type, notification_content)
VALUES
    (1, 'notice', '새로운 메시지가 도착했습니다.'),
    (2, 'notice', '예약이 확정되었습니다.'),
    (3, 'notice', '신고 접수되었습니다.'),
    (4, 'notice', '고객 평가가 등록되었습니다.'),
    (5, 'notice', '예약이 취소되었습니다.');

INSERT INTO cancel_reservation (reservation_id, reservation_num, checkin_date, checkout_date)
VALUES
    (1, 2, '2023-08-14', '2023-08-20'),
    (2, 4, '2023-08-16', '2023-08-22'),
    (3, 6, '2023-08-23', '2023-08-25');

