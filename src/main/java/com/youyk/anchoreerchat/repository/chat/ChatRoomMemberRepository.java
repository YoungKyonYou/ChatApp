package com.youyk.anchoreerchat.repository.chat;

import com.youyk.anchoreerchat.entity.chat.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long>, CustomChatRoomRepository {

}
