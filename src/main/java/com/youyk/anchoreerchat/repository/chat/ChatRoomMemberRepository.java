package com.youyk.anchoreerchat.repository.chat;

import com.youyk.anchoreerchat.entity.chat.ChatRoomMember;
import com.youyk.anchoreerchat.entity.message.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long>, CustomChatRoomMemberRepository {
    @Query("SELECT m.message FROM ChatRoomMember m WHERE m.chatRoomId = :chatRoomId")
    List<Message> findMessageByChatRoomId(@Param("chatRoomId") final Long chatRoomId);
    @Modifying
    @Transactional
    @Query("DELETE FROM ChatRoomMember crm WHERE crm.chatRoomId = :roomId")
    void deleteByChatRoomId(@Param("roomId") final Long chatRoomId);

}
