INSERT INTO customer (customer_email, customer_pw, delete_request, role, auth_state)
VALUES
    ('user1@example.com', 'password1', 'no', 'customer', 'active'),
    ('user2@example.com', 'password2', 'no', 'customer', 'active'),
    ('user3@example.com', 'password3', 'no', 'customer', 'active'),
    ('user4@example.com', 'password4', 'no', 'customer', 'active'),
    ('user5@example.com', 'password5', 'no', 'customer', 'active');

INSERT INTO reservation (rental_id, customer_id, reservation_num, checkin_date, checkout_date)
VALUES
    (1, 1, 2, '2023-08-10', '2023-08-15'),
    (2, 2, 1, '2023-08-12', '2023-08-18'),
    (3, 3, 3, '2023-08-14', '2023-08-20'),
    (4, 4, 1, '2023-08-16', '2023-08-22'),
    (5, 5, 2, '2023-08-18', '2023-08-25');

INSERT INTO house (host_id, title, description, max_capacity, min_capacity, price, location, image, auto_reservation, room, bed, bath_room)
VALUES
    (1, '편안한 산장', '매력적인 산장 휴양지...', 4, 2, 150, '산속 마을, CA', 'image1.jpg', 'yes', 3, 2, 2),
    (2, '해변가 파라다이스', '아름다운 바다가 내려다보이는...', 6, 4, 250, '말리부, CA', 'image2.jpg', 'yes', 4, 3, 2);

INSERT INTO report (feedback_host_id, feedback_customer_id, reported_customer, reporter, report_type, content)
VALUES
    (1, 2, 3, 4, '불만', '숙소 청결도에 불만이 있습니다.'),
    (2, 3, 4, 5, '신고', '부적절한 행동으로 신고합니다.'),
    (3, 4, 5, 1, '기타', '이유없이 신고해봅니다.');

INSERT INTO feedback_customer (customer_id, host_id, title, content, score_clean, score_communication, score_satisfaction)
VALUES
    (1, 1, '좋아요', '숙소 청결하고 편안했습니다.', 5, 4, 5),
    (2, 2, '만족합니다', '서비스가 매우 만족스러웠습니다.', 4, 5, 4),
    (3, 3, '괜찮아요', '보통 수준의 청결도와 서비스였습니다.', 3, 3, 3),
    (4, 4, '아쉬워요', '숙소 상태와 서비스가 아쉬웠습니다.', 2, 2, 2),
    (5, 5, '별로에요', '기대에 못 미치는 청결도와 서비스였습니다.', 1, 1, 1);

INSERT INTO feedback_host (customer_id, rental_id, title, content, attitude, disrepair)
VALUES
    (1, 1, '불편했습니다', '호스트의 태도가 좋지 않았습니다.', 2, 1),
    (2, 2, '불만족', '숙소 시설 상태가 좋지 않았습니다.', 1, 2),
    (3, 3, '보통입니다', '호스트의 태도와 숙소 시설이 보통 수준이었습니다.', 3, 3),
    (4, 4, '만족합니다', '호스트와 숙소 시설에 만족했습니다.', 4, 4),
    (5, 5, '좋아요', '호스트와 숙소 시설 모두 매우 좋았습니다.', 5, 5);

INSERT INTO host (customer_id, approve_state, lock_state)
VALUES
    (1, '승인', '미제한'),
    (2, '승인', '미제한'),
    (3, '승인', '미제한'),
    (4, '미승인', '미제한'),
    (5, '승인', '미제한');
    
INSERT INTO message (message_room, content, receive_customer_id, send_customer_id, read_check)
VALUES
    (1, '안녕하세요!', 2, 1, '읽음'),
    (2, '반갑습니다.', 1, 2, '읽지 않음'),
    (1, '어떤 숙소를 추천하시나요?', 3, 1, '읽지 않음'),
    (3, '물어보려고 했던 건데요...', 1, 3, '읽음'),
    (2, '예약 변경 가능한가요?', 2, 4, '읽음');

INSERT INTO customer_profile (customer_id, nickname, gender, birth_date, phone_num, public_at)
VALUES
    (1, '행복한여행자', 'M', '1990-05-15', 1234567890, '공개'),
    (2, '해변탐험가', 'F', '1985-08-22', 9876543210, '비공개'),
    (3, '자연사랑자', 'M', '1992-11-10', 5555555555, '공개'),
    (4, '도심탐험가', 'F', '1988-02-28', 7777777777, '공개'),
    (5, '문화체험가', 'M', '1995-04-17', 9999999999, '비공개');

INSERT INTO notification (customer_id, notification_type, notification_content)
VALUES
    (1, '알림', '새로운 메시지가 도착했습니다.'),
    (2, '알림', '예약이 확정되었습니다.'),
    (3, '알림', '신고 접수되었습니다.'),
    (4, '알림', '고객 평가가 등록되었습니다.'),
    (5, '알림', '예약이 취소되었습니다.');

INSERT INTO cancel_reservation (reservation_id, reservation_num, checkin_date, checkout_date)
VALUES
    (1, 2, '2023-08-12', '2023-08-15'),
    (2, 1, '2023-08-15', '2023-08-20'),
    (3, 3, '2023-08-18', '2023-08-24'),
    (4, 1, '2023-08-20', '2023-08-26'),
    (5, 2, '2023-08-23', '2023-08-28');
