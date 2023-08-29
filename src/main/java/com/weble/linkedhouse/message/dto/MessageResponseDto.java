package com.weble.linkedhouse.message.dto;

import com.weble.linkedhouse.message.entity.ChatMessage;
import lombok.Getter;

@Getter
public class MessageResponseDto {

    private Long id;
    private String content;
    private Long senderId;
    private Long receiverId;

    private MessageResponseDto(Long id, String content, Long senderId, Long receiverId) {
        this.id = id;
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public static MessageResponseDto from(ChatMessage chatMessage) {
        return new MessageResponseDto(chatMessage.getMessageId(),
                chatMessage.getContent(),
                chatMessage.getSender().getCustomerId(),
                chatMessage.getReceiver().getCustomerId());
    }
}
