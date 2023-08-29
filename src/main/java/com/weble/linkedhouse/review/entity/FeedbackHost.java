package com.weble.linkedhouse.review.entity;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.util.AuditingFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "createdAt")
})
public class FeedbackHost extends AuditingFields {

    @Id
    @Column(name = "feedback_host_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackHostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;      //리뷰 대상자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Customer writer;      // 리뷰쓰는 사람

    private String content;

    private String title;

    private int attitude;  //태도

    @Column(name = "damage_degree")
    private int damageDegree;  //파손

    private FeedbackHost(Customer customer, Customer writer, String title, String content, int attitude, int damageDegree) {
        this.customer = customer;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.attitude = attitude;
        this.damageDegree = damageDegree;
    }

    public static FeedbackHost of(Customer customer, Customer writer, String title, String content, int attitude, int damageDegree) {
        return new FeedbackHost(customer, writer, title, content, attitude, damageDegree);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeedbackHost that)) return false;
        return this.feedbackHostId != null && getFeedbackHostId().equals(that.getFeedbackHostId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFeedbackHostId());
    }

    public void updateReview(String content, int attitude, int damageDegree) {
        this.content = content;
        this.attitude = attitude;
        this.damageDegree = damageDegree;
    }
}