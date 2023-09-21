package com.weble.linkedhouse.message.controller;

import com.weble.linkedhouse.message.dto.ChatRoomRequest;
import com.weble.linkedhouse.message.dto.ChatRoomResponse;
import com.weble.linkedhouse.message.dto.MessageResponseDto;
import com.weble.linkedhouse.message.dto.MessageSendDto;
import com.weble.linkedhouse.message.service.ChattingService;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000" )
public class MessageController {

    private final ChattingService chattingService;
    private final SimpMessagingTemplate messagingTemplate;


    // 개인 채팅방 목록 조회
    @GetMapping("/rooms")
    public List<ChatRoomResponse> getChatRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chattingService.getChatRooms(userDetails);
    }


    // 채팅방 생성
    @PostMapping("/rooms")
    public ChatRoomResponse createChatRoom(@RequestBody ChatRoomRequest request) {
        return chattingService.createChatRoom(request);
    }


    // 채팅방 내 메시지 조회
    @GetMapping("/rooms/{roomId}/messages")
    public List<MessageResponseDto> getMessagesInRoom(@PathVariable Long roomId) {
        return chattingService.getMessagesInRoom(roomId);
    }

    // 1대1 채팅 메시지 전송
    @MessageMapping("/send/{roomId}")
    public void sendChatMessage(@DestinationVariable Long roomId,
                                @Payload MessageSendDto message,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) {

        messagingTemplate.convertAndSend("/topic/rooms/" + roomId, message);
        chattingService.sendChatMessage(roomId, message, userDetails);
    }
}
