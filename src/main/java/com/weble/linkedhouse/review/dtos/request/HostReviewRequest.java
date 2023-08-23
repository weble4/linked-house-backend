package com.weble.linkedhouse.review.dtos.request;

import com.weble.linkedhouse.review.entity.FeedbackHost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HostReviewRequest {

    private String title;
    private String content;
    private int attitude;
    private int damageDegree;

    @Builder
    private HostReviewRequest(String title,
                              String content, int attitude, int damageDegree) {
        this.title = title;
        this.content = content;
        this.attitude = attitude;
        this.damageDegree = damageDegree;
    }

    public static HostReviewRequest of(String title, String content, int attitude, int damageDegree) {
        return HostReviewRequest.builder()
                .title(title)
                .content(content)
                .attitude(attitude)
                .damageDegree(damageDegree)
                .build();
    }

    public static HostReviewRequest from(FeedbackHost feedbackHost){
        return HostReviewRequest.builder()
                .title(feedbackHost.getTitle())
                .content(feedbackHost.getContent())
                .attitude(feedbackHost.getAttitude())
                .damageDegree(feedbackHost.getDamageDegree())
                .build();
    }
}