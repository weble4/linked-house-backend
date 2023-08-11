package com.weble.linkedhouse.review.domain.entity;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.review.web.dtos.request.CustomerReviewRequest;
import com.weble.linkedhouse.util.AuditingFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedbackCustomer extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_customer_id")
    private Long feedbackcustomerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    private House house;

    private String title;

    private String content;

    @Column(name = "score_clean")
    private int scoreClean;

    @Column(name = "score_communication")
    private int scoreCommunication;

    @Column(name = "score_satisfaction")
    private int scoreSatisfaction;

    @Column(name = "total_score")
    private int totalScore;

    @Builder
    private FeedbackCustomer(Customer customer, House house,
                             String title, String content, int scoreClean, int scoreCommunication
                             , int scoreSatisfaction, int totalScore){
        this.customer = customer;
        this.house = house;
        this.title = title;
        this.content = content;
        this.scoreClean = scoreClean;
        this.scoreCommunication = scoreCommunication;
        this. scoreSatisfaction = scoreSatisfaction;
        this.totalScore = totalScore;
    }

    public static FeedbackCustomer of(Customer customer, House house,
                                      String title, String content, int scoreClean, int scoreCommunication,
                                      int scoreSatisfaction, int totalScore) {
        return FeedbackCustomer.builder()
                .customer(customer)
                .house(house)
                .title(title)
                .content(content)
                .scoreClean(scoreClean)
                .scoreCommunication(scoreCommunication)
                .scoreSatisfaction(scoreSatisfaction)
                .totalScore(totalScore)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeedbackCustomer that)) return false;
        return this.feedbackcustomerId != null && getFeedbackcustomerId().equals(that.getFeedbackcustomerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFeedbackcustomerId());
    }

    public void updateCustomerReview(CustomerReviewRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.scoreClean = request.getScoreClean();
        this.scoreCommunication = request.getScoreCommunication();
        this.scoreSatisfaction = request.getScoreSatisfaction();
        this.totalScore = request.getTotalScore();
    }
}
