package com.weble.linkedhouse.review.entity;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.util.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "createdAt")
})
public class FeedbackCustomer extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_customer_id")
    private Long feedbackCustomerId;

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
    private double totalScore;

    @Builder
    private FeedbackCustomer(Customer customer, House house, String title, String content,
                             int scoreClean, int scoreCommunication, int scoreSatisfaction){
        this.customer = customer;
        this.house = house;
        this.title = title;
        this.content = content;
        this.scoreClean = scoreClean;
        this.scoreCommunication = scoreCommunication;
        this.scoreSatisfaction = scoreSatisfaction;
    }

    public static FeedbackCustomer of(Customer customer, House house, String title, String content,
                                      int scoreClean, int scoreCommunication, int scoreSatisfaction) {
        return new FeedbackCustomer(customer, house, title, content, scoreClean, scoreCommunication, scoreSatisfaction);

    }

    public void updateReview(String content, int scoreClean, int scoreCommunication, int scoreSatisfaction) {
        this.content = content;
        this.scoreClean = scoreClean;
        this.scoreCommunication = scoreCommunication;
        this.scoreSatisfaction = scoreSatisfaction;
    }

    public void calculate() {
        this.totalScore = Math.round((scoreClean + scoreCommunication + scoreSatisfaction) / 3.0 * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeedbackCustomer that)) return false;
        return this.feedbackCustomerId != null && getFeedbackCustomerId().equals(that.getFeedbackCustomerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFeedbackCustomerId());
    }
}
