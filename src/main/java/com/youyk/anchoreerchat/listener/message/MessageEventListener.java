package com.youyk.anchoreerchat.listener.message;


import com.youyk.anchoreerchat.entity.chat.ChatMessage;
import com.youyk.anchoreerchat.entity.chat.ChatRoomMember;
import com.youyk.anchoreerchat.entity.message.Message;
import com.youyk.anchoreerchat.repository.chat.ChatRoomMemberRepository;
import com.youyk.anchoreerchat.repository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class MessageEventListener {
    private final MessageRepository messageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Async
    @EventListener
    public void saveMessage(final ChatMessage receivedMessage) {
        //채팅 메시지 저장
        final Message message = messageRepository.save(Message.from(receivedMessage));
        final ChatRoomMember chatRoomMember = ChatRoomMember.of(receivedMessage.senderId(), receivedMessage.chatRoomId(), message);
        chatRoomMemberRepository.save(chatRoomMember);
    }
}
