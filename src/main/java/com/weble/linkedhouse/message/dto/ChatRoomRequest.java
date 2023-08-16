package com.weble.linkedhouse.message.dto;

import com.weble.linkedhouse.message.entity.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomRequest {

    private Long receiverId;
    private Long senderId;


    private ChatRoomRequest(Long receiverId, Long senderId) {
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

    public static ChatRoomRequest from(ChatRoom chatRoom) {
        return new ChatRoomRequest(
                chatRoom.getReceiver().getCustomerId(),
                chatRoom.getSender().getCustomerId()
        );
    }
}
