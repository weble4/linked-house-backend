package com.weble.linkedhouse.bookmark.controller;

import com.weble.linkedhouse.bookmark.dto.BookmarkResponse;
import com.weble.linkedhouse.bookmark.service.BookMarkService;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @GetMapping
    public ResponseEntity<List<BookmarkResponse>> getBookMarkList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BookmarkResponse> bookmakrList = bookMarkService.getBookMarkList(userDetails);
        return ResponseEntity.ok().body(bookmakrList);
    }

    @PostMapping("/{rentalId}")
    public ResponseEntity<String> addBookmark(@PathVariable Long rentalId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        bookMarkService.addBookmark(userDetails, rentalId);
        return ResponseEntity.ok("북마크 추가하였습니다.");
    }

    @DeleteMapping("/rentalId}")
    public ResponseEntity<String> deleteBookmark(@PathVariable Long rentalId,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        bookMarkService.deleteBookmark(rentalId, userDetails.getUserId());
        return ResponseEntity.ok("삭제하였습니다");
    }
}
