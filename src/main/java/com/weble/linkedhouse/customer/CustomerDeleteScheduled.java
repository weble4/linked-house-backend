package com.weble.linkedhouse.customer;

import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.customer.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class CustomerDeleteScheduled {

    private final CustomerRepository customerRepository;
    private final ProfileRepository profileRepository;
    private final JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 0 5 * * ?") // 매일 새벽 5시에 실행
    public void deleteCustomer() {
        log.info("삭제 요청후 30 일 경과 아이디 삭제");

        List<String> tableNames = getDeleteTableNames();
        for (String tableName : tableNames) {
            deleteCustomerAndTable(tableName);
        }
    }

    private List<String> getDeleteTableNames() {
        String queryTableNames = "SELECT table_name FROM information_schema.tables WHERE table_name LIKE 'DELETE_%'";
        return jdbcTemplate.queryForList(queryTableNames.toUpperCase(), String.class);
    }

    private void deleteCustomerAndTable(String tableName) {
        String queryRequestAt = "SELECT customer_id, request_at FROM " + tableName;
        jdbcTemplate.query(queryRequestAt, (rs, rowNum) -> {
            Long customerId = rs.getLong("customer_id");
            Timestamp requestAt = rs.getTimestamp("request_at");

            if (requestAt.toLocalDateTime().toLocalDate().equals(LocalDateTime.now().toLocalDate())) {
                dropTable(tableName);
                deleteCustomerData(customerId, tableName);
            }
            return null;
        });
    }

    private void deleteCustomerData(Long customerId, String tableName) {
        profileRepository.deleteById(customerId);
        String deleteDataSql = "DELETE FROM " + tableName + " WHERE customer_id = ?";
        jdbcTemplate.update(deleteDataSql, customerId);
        customerRepository.deleteById(customerId);
    }

    private void dropTable(String tableName) {
        String dropTableSql = "DROP TABLE " + tableName.toUpperCase();
        jdbcTemplate.update(dropTableSql);
    }
}
