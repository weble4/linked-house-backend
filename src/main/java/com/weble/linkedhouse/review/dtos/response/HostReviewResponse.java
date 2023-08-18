package com.weble.linkedhouse.review.dtos.response;

import com.weble.linkedhouse.customer.dtos.CustomerDto;
import com.weble.linkedhouse.review.domain.entity.FeedbackHost;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter                                  // responseDTO의 데이터를 호출하기 위해 @Getter가 필요함
public class HostReviewResponse {

    private CustomerDto writer;
    private CustomerDto customerDto;
    private String title;
    private String content;
    private int attitude;
    private int damageDegree;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private HostReviewResponse(CustomerDto writer, CustomerDto customerDto, String title, String content,
                               int attitude, int damageDegree, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.writer = writer;
        this.customerDto = customerDto;
        this.title = title;
        this.content = content;
        this.attitude = attitude;
        this.damageDegree = damageDegree;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static HostReviewResponse of(CustomerDto writer, CustomerDto customerDto, String title, String content,
                                        int attitude, int damageDegree, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return HostReviewResponse.builder()
                .writer(writer)
                .customerDto(customerDto)
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
                .writer(CustomerDto.from(feedbackHost.getWriter()))
                .customerDto(CustomerDto.from(feedbackHost.getCustomer()))
                .title(feedbackHost.getTitle())
                .content(feedbackHost.getContent())
                .attitude(feedbackHost.getAttitude())
                .damageDegree(feedbackHost.getDamageDegree())
                .createdAt(feedbackHost.getCreatedAt())
                .updatedAt(feedbackHost.getUpdatedAt())
                .build();
    }

}