package com.weble.linkedhouse.message;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.message.constant.ReadCheck;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(name = "message_room")
    private int messageRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "recieve_customer")
    @JoinColumn(name = "customerId")
    private Customer recieveCustomer;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "send_customer")
    @JoinColumn(name = "customerId")
    private Customer sendCustomer;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private ReadCheck readCheck;  // READ, UNREAD

    @Column(name = "send_time")
    private LocalDateTime sendTime;

    @Column(name = "read_time")
    private LocalDateTime readTime;

    private Message(int messageRoom, Customer recieveCustomer, Customer sendCustomer, String content) {
        this.messageRoom = messageRoom;
        this.recieveCustomer = recieveCustomer;
        this.sendCustomer = sendCustomer;
        this.content = content;
    }

    public static Message of(int messageRoom, Customer recieveCustomer, Customer sendCustomer, String content) {
        return new Message(messageRoom, recieveCustomer, sendCustomer, content);
    }
}
