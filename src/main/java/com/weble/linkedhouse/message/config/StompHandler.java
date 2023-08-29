package com.weble.linkedhouse.message.config;

import com.weble.linkedhouse.exception.AboutJwtException;
import com.weble.linkedhouse.security.jwt.JwtReturn;
import com.weble.linkedhouse.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getCommand() == StompCommand.CONNECT) {
            if (isTokenSuccess(accessor)) {
                throw new AboutJwtException();
            }
        }
        return message;
    }

    private boolean isTokenSuccess(StompHeaderAccessor accessor) {
        return jwtTokenProvider.validToken(accessor.getFirstNativeHeader("Authorization")) != JwtReturn.SUCCESS;
    }
}
