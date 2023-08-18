package com.weble.linkedhouse.review.dtos.request;

import com.weble.linkedhouse.customer.dtos.CustomerDto;
import com.weble.linkedhouse.review.entity.FeedbackHost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HostReviewRequest {

    private CustomerDto writer;
    private CustomerDto customerDto;
    private String title;
    private String content;
    private int attitude;
    private int damageDegree;

    @Builder
    private HostReviewRequest(CustomerDto writer, CustomerDto customerDto, String title,
                              String content, int attitude, int damageDegree) {
        this.writer = writer;
        this.customerDto = customerDto;
        this.title = title;
        this.content = content;
        this.attitude = attitude;
        this.damageDegree = damageDegree;
    }

    public static HostReviewRequest of(CustomerDto writer, CustomerDto customerDto, String title,
                                       String content, int attitude, int damageDegree) {
        return HostReviewRequest.builder()
                .writer(writer)
                .customerDto(customerDto)
                .title(title)
                .content(content)
                .attitude(attitude)
                .damageDegree(damageDegree)
                .build();
    }

    public static HostReviewRequest from(FeedbackHost feedbackHost){
        return HostReviewRequest.builder()
                .writer(CustomerDto.from(feedbackHost.getWriter()))
                .customerDto(CustomerDto.from(feedbackHost.getCustomer()))
                .title(feedbackHost.getTitle())
                .content(feedbackHost.getContent())
                .attitude(feedbackHost.getAttitude())
                .damageDegree(feedbackHost.getDamageDegree())
                .build();
    }

    public FeedbackHost toEntity() {
        return FeedbackHost.of(customerDto.toEntity(), writer.toEntity(), title, content, attitude, damageDegree);
    }
}