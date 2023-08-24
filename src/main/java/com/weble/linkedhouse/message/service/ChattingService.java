package com.weble.linkedhouse.message.service;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistCustomer;
import com.weble.linkedhouse.exception.NotFoundChattingRoomException;
import com.weble.linkedhouse.message.dto.ChatRoomRequest;
import com.weble.linkedhouse.message.dto.ChatRoomResponse;
import com.weble.linkedhouse.message.dto.MessageResponseDto;
import com.weble.linkedhouse.message.dto.MessageSendDto;
import com.weble.linkedhouse.message.entity.ChatMessage;
import com.weble.linkedhouse.message.entity.ChatRoom;
import com.weble.linkedhouse.message.repository.ChatMessageRepository;
import com.weble.linkedhouse.message.repository.ChatRoomRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChattingService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository messageRepository;
    private final CustomerRepository customerRepository;

    // 채팅방 목록 조회
    public List<ChatRoomResponse> getChatRooms(UserDetailsImpl userDetails) {
        return chatRoomRepository.findChatRoomsByUserId(userDetails.getUserId())
                .stream().map(ChatRoomResponse::from)
                .collect(Collectors.toList());
    }

    // 채팅방 생성
    @Transactional
    public ChatRoomResponse createChatRoom(ChatRoomRequest request) {

        Customer sender = customerRepository.findById(request.getSenderId())
                .orElseThrow(NotExistCustomer::new);
        Customer receiver = customerRepository.findById(request.getReceiverId())
                .orElseThrow(NotExistCustomer::new);

        ChatRoom chatRoom = ChatRoom.of(receiver, sender);
        ChatRoom save = chatRoomRepository.save(chatRoom);

        return ChatRoomResponse.from(save);
    }

    // 채팅방 내 메시지 조회
    public List<MessageResponseDto> getMessagesInRoom(Long roomId) {
        return messageRepository.findByChatRoomRoomId(roomId)
                .stream().map(MessageResponseDto::from)
                .collect(Collectors.toList());
    }

    // 1대1 채팅 메시지 전송 (웹소켓을 통해 처리)
    @Transactional
    public void sendChatMessage(Long roomId, MessageSendDto message, UserDetailsImpl userDetails) {

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(NotFoundChattingRoomException::new);

        Customer sender = customerRepository.findById(userDetails.getUserId()).orElseThrow();

        // 메시지 객체 생성
        ChatMessage chatMessage = ChatMessage.of(
                chatRoom,
                message.getContent(),
                sender,
                chatRoom.getReceiver());

        messageRepository.save(chatMessage);
    }
}
