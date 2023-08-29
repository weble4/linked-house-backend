package com.weble.linkedhouse.review.dtos.response;

import com.weble.linkedhouse.review.entity.FeedbackCustomer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class CustomerReviewResponse {

    private Long feedbackCustomerId;
    private Long customerId;
    private Long rentalId;
    private String title;
    private String content;
    private int scoreClean;
    private int scoreCommunication;
    private int scoreSatisfaction;
    private int totalScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private CustomerReviewResponse(Long feedbackCustomerId, Long customerId, Long rentalId, String title, String content,
                                  int scoreClean, int scoreCommunication, int scoreSatisfaction, int totalScore,
                                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.feedbackCustomerId = feedbackCustomerId;
        this.customerId = customerId;
        this.rentalId = rentalId;
        this.title = title;
        this.content = content;
        this.scoreClean = scoreClean;
        this.scoreCommunication = scoreCommunication;
        this.scoreSatisfaction = scoreSatisfaction;
        this.totalScore = totalScore;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CustomerReviewResponse of(Long feedbackCustomerId, Long customerId, Long rentalId, String title, String content,
                                     int scoreClean, int scoreCommunication, int scoreSatisfaction, int totalScore,
                                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        return CustomerReviewResponse.builder()
                .feedbackCustomerId(feedbackCustomerId)
                .customerId(customerId)
                .rentalId(rentalId)
                .title(title)
                .content(content)
                .scoreClean(scoreClean)
                .scoreCommunication(scoreCommunication)
                .scoreSatisfaction(scoreSatisfaction)
                .totalScore(totalScore)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static CustomerReviewResponse from(FeedbackCustomer feedbackCustomer){
        return CustomerReviewResponse.builder()
                .feedbackCustomerId(feedbackCustomer.getFeedbackCustomerId())
                .customerId(feedbackCustomer.getCustomer().getCustomerId())
                .rentalId(feedbackCustomer.getHouse().getRentalId())
                .title(feedbackCustomer.getTitle())
                .content(feedbackCustomer.getContent())
                .scoreClean(feedbackCustomer.getScoreClean())
                .scoreCommunication(feedbackCustomer.getScoreCommunication())
                .scoreSatisfaction(feedbackCustomer.getScoreSatisfaction())
                .createdAt(feedbackCustomer.getCreatedAt())
                .updatedAt(feedbackCustomer.getUpdatedAt())
                .build();
    }

}