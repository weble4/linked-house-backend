package com.weble.linkedhouse.review.dtos.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomerReviewRequest {

    private String title;
    private String content;
    private int scoreClean;
    private int scoreCommunication;
    private int scoreSatisfaction;

    @Builder
    public CustomerReviewRequest(String title, String content, int scoreClean,
                                 int scoreCommunication, int scoreSatisfaction) {
        this.title = title;
        this.content = content;
        this.scoreClean = scoreClean;
        this.scoreCommunication = scoreCommunication;
        this.scoreSatisfaction = scoreSatisfaction;
    }

    public static CustomerReviewRequest of(String title, String content, int scoreClean,
                                           int scoreCommunication, int scoreSatisfaction){
        return CustomerReviewRequest.builder()
                .title(title)
                .content(content)
                .scoreClean(scoreClean)
                .scoreCommunication(scoreCommunication)
                .scoreSatisfaction(scoreSatisfaction)
                .build();
    }
}
