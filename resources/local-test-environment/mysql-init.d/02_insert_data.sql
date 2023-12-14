use golajuma_test;

delete from user;
delete from vote;
delete from vote_option;
delete from decision;
delete from comment;

-- 수정시 비율 vote:vote_option:decision 1:1:2


DELIMITER //
CREATE PROCEDURE InsertUserData()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 100 DO
        INSERT INTO user (created_date, deleted, updated_date, user_email, user_nickname, user_password)
        VALUES (NOW(), false, NOW(), CONCAT('user', i, '@example.com'), CONCAT('User', i), CONCAT('password', i));

        SET i = i + 1;
    END WHILE;
END //
DELIMITER ;

-- vote, vote_option은 시간간격을 주기 위해 2시간 전부터 1분간격으로 생성됨 옵션은 2개 제공. 같이 사이클을 돌림으로서 id가 안맞는 문제를 해결
-- 개수 변화를 주고싶을 시 : vote변화 - while i <= 1000을 바꿈. vote_option 변화 - vote_option의 values의 개수를 늘림
DELIMITER //
CREATE PROCEDURE InsertVoteAndOptionData()
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i <= 1000 DO
        INSERT INTO vote (created_date, deleted, updated_date, vote_category, user_id, vote_content, vote_end_date, vote_title, vote_total_count, vote_type)
        VALUES (NOW() - INTERVAL 2 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 2 HOUR + INTERVAL i MINUTE, 'TOTAL', FLOOR(1 + RAND() * 100), CONCAT('Vote Content ', i), NOW() + INTERVAL 2 DAY, CONCAT('Vote Title ', i), 10, null);

        INSERT INTO vote_option (created_date, deleted, updated_date, option_name, option_count, option_vote_id, option_image)
        VALUES (NOW() - INTERVAL 2 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 2 HOUR + INTERVAL i MINUTE, CONCAT('Option Name ', i), 5, i, null),
                (NOW() - INTERVAL 2 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 2 HOUR + INTERVAL i MINUTE, CONCAT('Option Name ', i+1), 5, i, null);

        SET i = i + 1;
    END WHILE;
END //
DELIMITER ;

-- 1~10번까지의 유저가 투표를 각 옵션에 5개씩 투표한 시나리오. 각 유저는 투표 후, 댓글을 단다.
-- 각각 개수 수정 시, values의 행의 개수를 늘림. i = 각 투표의 첫번째 옵션, i+1 = 각 투표의 두번째 옵션
DELIMITER //
CREATE PROCEDURE InsertDecisionData()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE j INT DEFAULT 1;
    WHILE i <= 2000 DO
        INSERT INTO decision (created_date, deleted, updated_date, decision_user_id, decision_option_id)
        VALUES
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,j,i),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,j+1,i),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,j+2,i),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,j+3,i),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,j+4,i),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,j+5,i+1),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,j+6,i+1),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,j+7,i+1),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,j+8,i+1),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,j+9,i+1);

        SET i = i + 2;
    END WHILE;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE InsertCommentData()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE j INT DEFAULT 1;
    WHILE i <= 1000 DO
        INSERT INTO comment (created_date, deleted, updated_date, comment_vote_id, comment_user_id, comment_content)
        VALUES
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,i, j, CONCAT('Comment Content ', j)),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,i, j+1, CONCAT('Comment Content ', j+1)),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,i, j+2, CONCAT('Comment Content ', j+2)),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,i, j+3, CONCAT('Comment Content ', j+3)),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,i, j+4, CONCAT('Comment Content ', j+4)),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,i, j+5, CONCAT('Comment Content ', j+5)),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,i, j+6, CONCAT('Comment Content ', j+6)),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,i, j+7, CONCAT('Comment Content ', j+7)),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,i, j+8, CONCAT('Comment Content ', j+8)),
            (NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 1 HOUR + INTERVAL i MINUTE,i, j+9, CONCAT('Comment Content ', j+9));

        SET i = i + 1;
END WHILE;
END //
DELIMITER ;

CALL InsertUserData();
CALL InsertVoteAndOptionData();
CALL InsertDecisionData();
CALL InsertCommentData();

-- DELIMITER //
-- CREATE PROCEDURE InsertVoteOptionData()
-- BEGIN
--     DECLARE i INT DEFAULT 1;
--
--     WHILE i <= 1000 DO
--         INSERT INTO vote_option (created_date, deleted, updated_date, option_name, option_count, option_vote_id, option_image)
--         VALUES (NOW() - INTERVAL 2 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 2 HOUR + INTERVAL i MINUTE, CONCAT('Option Name ', i), 5, i, null),
--             (NOW() - INTERVAL 2 HOUR + INTERVAL i MINUTE, false, NOW() - INTERVAL 2 HOUR + INTERVAL i MINUTE, CONCAT('Option Name ', i+1), 5, i+1, null);
--
--         SET i = i + 2;
--     END WHILE;
-- END //
-- DELIMITER ;