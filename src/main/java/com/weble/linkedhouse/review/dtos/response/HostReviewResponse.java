package com.weble.linkedhouse.review.dtos.response;

import com.weble.linkedhouse.review.entity.FeedbackHost;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter                                  // responseDTO의 데이터를 호출하기 위해 @Getter가 필요함
public class HostReviewResponse {

    private Long feedbackHostId;
    private Long writerId;
    private Long customerId;
    private String title;
    private String content;
    private int attitude;
    private int damageDegree;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private HostReviewResponse(Long feedbackHostId, Long writerId, Long customerId, String title, String content,
                               int attitude, int damageDegree, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.feedbackHostId = feedbackHostId;
        this.writerId = writerId;
        this.customerId = customerId;
        this.title = title;
        this.content = content;
        this.attitude = attitude;
        this.damageDegree = damageDegree;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static HostReviewResponse of(Long feedbackHostId, Long writerId, Long customerId, String title, String content,
                                        int attitude, int damageDegree, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return HostReviewResponse.builder()
                .feedbackHostId(feedbackHostId)
                .writerId(writerId)
                .customerId(customerId)
                .title(title)
                .content(content)
                .attitude(attitude)
                .damageDegree(damageDegree)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static HostReviewResponse from(FeedbackHost feedbackHost){
        return HostReviewResponse.builder()
                .feedbackHostId(feedbackHost.getFeedbackHostId())
                .writerId(feedbackHost.getWriter().getCustomerId())
                .customerId(feedbackHost.getCustomer().getCustomerId())
                .title(feedbackHost.getTitle())
                .content(feedbackHost.getContent())
                .attitude(feedbackHost.getAttitude())
                .damageDegree(feedbackHost.getDamageDegree())
                .createdAt(feedbackHost.getCreatedAt())
                .updatedAt(feedbackHost.getUpdatedAt())
                .build();
    }

}