package com.weble.linkedhouse.message.repository;

import com.weble.linkedhouse.message.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoomRoomId(Long roomId);

}
