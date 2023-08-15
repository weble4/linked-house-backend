package com.weble.linkedhouse.message.repository;

import com.weble.linkedhouse.message.entity.ChatRoom;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT c FROM ChatRoom c WHERE c.receiver.id = :userId OR c.sender.id = :userId")
    List<ChatRoom> findChatRoomsByUserId(@Param("userId") Long userId);
}
