package com.weble.linkedhouse.review.web.dtos.response;

import com.weble.linkedhouse.customer.dtos.CustomerDto;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.review.domain.entity.FeedbackCustomer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class CustomerReviewResponse {
    private CustomerDto customerDto;
    private House house;
    private String title;
    private String content;
    private int scoreClean;
    private int scoreCommunication;
    private int scoreSatisfaction;
    private int totalScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private CustomerReviewResponse(CustomerDto customerDto, House house, String title, String content,
                                  int scoreClean, int scoreCommunication, int scoreSatisfaction, int totalScore,
                                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.customerDto = customerDto;
        this.house = house;
        this.title = title;
        this.content = content;
        this.scoreClean = scoreClean;
        this.scoreCommunication = scoreCommunication;
        this.scoreSatisfaction = scoreSatisfaction;
        this.totalScore = totalScore;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CustomerReviewResponse of(CustomerDto customerDto, House house, String title, String content,
                                     int scoreClean, int scoreCommunication, int scoreSatisfaction, int totalScore,
                                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        return CustomerReviewResponse.builder()
                .customerDto(customerDto)
                .house(house)
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
                .customerDto(CustomerDto.from(feedbackCustomer.getCustomer()))
                .house(feedbackCustomer.getHouse())
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