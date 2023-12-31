package com.weble.linkedhouse.bookmark.repository;

import com.weble.linkedhouse.bookmark.dto.BookmarkResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookMarkRepositoryImpl implements BookMarkRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean isTableExists(String tableName) {
        String checkTableQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        //실제 DB에서는 소문자로 테이블 이름이 들어갈 시 생성은 상관이 없었지만, 검색에서는 되지 않음
        //uppercase가 되어야 테이블을 제대로 검색 하였다.

        Integer count = jdbcTemplate.queryForObject(checkTableQuery, Integer.class, tableName.toUpperCase());

        log.info("테이블 개수 : {}", count);

        return count != null && count > 0;
    }

    @Override
    public void createTable(String tableName, Long customerId) {
        String createTableQuery = """
                CREATE TABLE %s (
                    customer_id BIGINT NOT NULL,
                    rental_id BIGINT NOT NULL
                )""".formatted(tableName);
        jdbcTemplate.execute(createTableQuery);
    }

    @Override
    public void save(String tableName, Long customerId, Long rentalId) {
        String insertQuery = """
                INSERT INTO %s
                (customer_id, rental_id)
                VALUES (?, ?)""".formatted(tableName);
        jdbcTemplate.update(insertQuery, customerId, rentalId);
    }

    @Override
    public List<BookmarkResponse> getBookmark(String tableName, Long customerId) {

        String findQuery = """
                SELECT b.customer_id, b.rental_id, h.price, h.location, hi.image_path
                FROM %s AS b
                JOIN house AS h ON b.rental_id = h.rental_id
                LEFT JOIN (
                    SELECT rental_id, image_path
                    FROM house_image
                    LIMIT 1
                ) AS hi ON h.rental_id = hi.rental_id
                """.formatted(tableName);

        return jdbcTemplate.query(findQuery, (rs, rowNum) -> {
            Long rentalId = rs.getLong("rental_id");
            int price = rs.getInt("price");
            String location = rs.getString("location");
            String imagePath = rs.getString("image_path");
            return BookmarkResponse.of(rentalId, price, location, imagePath);
        });
    }

    @Override
    public void deleteByRentalId(String tableName, Long rentalId) {
        String deleteQuery = "DELETE FROM " + tableName + " WHERE rental_id = ?";
        jdbcTemplate.update(deleteQuery, rentalId);
    }

    @Override
    public void deleteTable(String tableName) {
        String deleteQuery = "DROP TABLE " + tableName;
        jdbcTemplate.execute(deleteQuery);
    }
}
