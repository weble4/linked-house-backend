package com.weble.linkedhouse.message.dto;

import com.weble.linkedhouse.message.entity.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomResponse {

    private Long chatRoomId;
    private Long receiverId;
    private Long senderId;

    public ChatRoomResponse(Long chatRoomId, Long receiverId, Long senderId) {
        this.chatRoomId = chatRoomId;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

    public static ChatRoomResponse from(ChatRoom chatRoom) {
        return new ChatRoomResponse(chatRoom.getRoomId(),
                chatRoom.getReceiver().getCustomerId(),
                chatRoom.getSender().getCustomerId());
    }
}
