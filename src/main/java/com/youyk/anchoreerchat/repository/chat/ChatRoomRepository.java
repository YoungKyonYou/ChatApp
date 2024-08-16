package com.youyk.anchoreerchat.repository.chat;

import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, CustomChatRoomRepository {
    Optional<ChatRoom> findByRoomName(String roomName);
    void deleteByRoomName(String roomName);
}
