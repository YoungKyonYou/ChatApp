package com.youyk.anchoreerchat.repository.participant;

import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import com.youyk.anchoreerchat.entity.participant.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Query("""
            SELECT c
              FROM Participant p
         LEFT JOIN ChatRoom c
                ON c.roomId = p.chatRoomId
         LEFT JOIN Member m
                ON m.memberId = p.memberId
             GROUP BY p.chatRoomId
            HAVING m.loginDateTime >= :thirtyMinutesAgo
             ORDER BY count(m)
              DESC
               """)
    List<ChatRoom> findChatRoomByMemberLoginDateTimeOrderByMemberCountDesc(@Param("thirtyMinutesAgo") LocalDateTime thirtyMinutesAgo);

}
