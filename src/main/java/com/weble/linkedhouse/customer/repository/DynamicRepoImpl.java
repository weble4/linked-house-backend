package com.weble.linkedhouse.customer.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DynamicRepoImpl implements DynamicRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void deleteAccount(String tableName, Long userId) {

        String createTableSql = """
                CREATE TABLE delete_%s (
                    customer_id BIGINT PRIMARY KEY,
                    request_at TIMESTAMP,
                    expected_at TIMESTAMP
                ); """.formatted(tableName);

        try {
            jdbcTemplate.execute(createTableSql);
            log.info("유저 삭제 테이블이 생성 되었습니다. - {}", tableName);
        } catch (DataAccessException e) {
            log.info("이미 존재하는 테이블입니다.");
        }

        LocalDateTime requestAt = LocalDateTime.now();
        LocalDateTime expectedAt = requestAt.plusDays(30);

        String insertSql = """
                INSERT INTO delete_%s (customer_id, request_at, expected_at)
                VALUES (?, ?, ?);
                """.formatted(tableName);
        log.info("{} 에 정보가 제대로 들어갔습니다.",tableName );

        jdbcTemplate.update(insertSql, userId, Timestamp.valueOf(requestAt), Timestamp.valueOf(expectedAt));
    }
}
