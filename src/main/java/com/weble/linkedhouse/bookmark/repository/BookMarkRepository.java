package com.weble.linkedhouse.bookmark.repository;

import com.weble.linkedhouse.bookmark.dto.BookmarkResponse;

import java.util.List;

public interface BookMarkRepository {

    boolean isTableExists(String tableName);

    void createTable(String tableName, Long customerId);

    void save(String tableName, Long userId, Long rentalId);

    List<BookmarkResponse> getBookmark(String tableName, Long userId);

    void deleteByRentalId(String tableName, Long rentalId);

    void deleteTable(String tableName);
}
