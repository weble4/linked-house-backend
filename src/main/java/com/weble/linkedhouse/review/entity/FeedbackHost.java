package com.weble.linkedhouse.review.entity;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.util.AuditingFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedbackHost extends AuditingFields {

    @Id
    @Column(name = "feedback_host_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackHostId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;      //리뷰 대상자

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer writer;      // 리뷰쓰는 사람

    private String content;

    private int attitude;  //태도

    @Column(name = "damage_degree")
    private int damageDegree;  //파손

    private FeedbackHost(Customer customer, Customer writer, String content, int attitude, int damageDegree) {
        this.customer = customer;
        this.writer = writer;
        this.content = content;
        this.attitude = attitude;
        this.damageDegree = damageDegree;
    }

    public static FeedbackHost of(Customer customer, Customer writer, String content, int attitude, int damageDegree) {
        return new FeedbackHost(customer, writer, content, attitude, damageDegree);
    }
}
