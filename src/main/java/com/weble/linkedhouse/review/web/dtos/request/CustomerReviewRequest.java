package com.weble.linkedhouse.review.web.dtos.request;

import com.weble.linkedhouse.customer.dtos.CustomerDto;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.review.domain.entity.FeedbackCustomer;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomerReviewRequest {
    private CustomerDto customerDto;
    private House house;
    private String title;
    private String content;
    private int scoreClean;
    private int scoreCommunication;
    private int scoreSatisfaction;
    private double totalScore;

    @Builder
    public CustomerReviewRequest(CustomerDto customerDto, House house, String title, String content,
                                 int scoreClean, int scoreCommunication, int scoreSatisfaction) {
        this.customerDto = customerDto;
        this.house = house;
        this.title = title;
        this.content = content;
        this.scoreClean = scoreClean;
        this.scoreCommunication = scoreCommunication;
        this.scoreSatisfaction = scoreSatisfaction;
        this.totalScore = (double) (scoreClean * scoreCommunication * scoreSatisfaction) / 3.0;
    }

    public static CustomerReviewRequest of(CustomerDto customerDto, House house, String title, String content,
                                           int scoreClean, int scoreCommunication, int scoreSatisfaction){
        return CustomerReviewRequest.builder()
                .customerDto(customerDto)
                .house(house)
                .title(title)
                .content(content)
                .scoreClean(scoreClean)
                .scoreCommunication(scoreCommunication)
                .scoreSatisfaction(scoreSatisfaction)
                .build();
    }

    public static CustomerReviewRequest from(FeedbackCustomer feedbackCustomer){
        return CustomerReviewRequest.builder()
                .customerDto(CustomerDto.from(feedbackCustomer.getCustomer()))
                .house(feedbackCustomer.getHouse())
                .title(feedbackCustomer.getTitle())
                .content(feedbackCustomer.getContent())
                .scoreClean(feedbackCustomer.getScoreClean())
                .scoreCommunication(feedbackCustomer.getScoreCommunication())
                .scoreSatisfaction(feedbackCustomer.getScoreSatisfaction())
                .build();
    }

    public FeedbackCustomer toEntity() {
        return FeedbackCustomer.of(customerDto.toEntity(), house, title, content,
                scoreClean, scoreCommunication, scoreSatisfaction);
    }

}