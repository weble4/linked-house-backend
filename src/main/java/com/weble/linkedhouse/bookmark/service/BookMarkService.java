package com.weble.linkedhouse.bookmark.service;

import com.weble.linkedhouse.bookmark.dto.BookmarkResponse;
import com.weble.linkedhouse.bookmark.repository.BookMarkRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;

    @Transactional
    public void addBookmark(UserDetailsImpl userDetails, Long rentalId) {
        Long customerId = userDetails.getUserId();

        String tableName = "bookmark_customer" + customerId;

        if (!bookMarkRepository.isTableExists(tableName)) {
            bookMarkRepository.createTable(tableName, customerId);
            log.info("북마크 테이블을 생성하였습니다.");
        }

        bookMarkRepository.save(tableName, customerId,rentalId);
    }

    @Transactional
    public List<BookmarkResponse> getBookMarkList(UserDetailsImpl userDetails) {
        Long customerId = userDetails.getUserId();
        return bookMarkRepository.getBookmark(customerId);
    }

    @Transactional
    public void deleteBookmark(Long rentalId, Long customerId) {
        bookMarkRepository.deleteByRentalId(rentalId, customerId);
    }
}
