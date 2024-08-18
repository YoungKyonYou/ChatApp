package com.youyk.anchoreerchat.repository.participant;

import com.youyk.anchoreerchat.entity.participant.Participant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findParticipantsByChatRoomId(final Long roomId);

    @Query("SELECT p.memberId FROM Participant p WHERE p.chatRoomId = :roomId")
    List<Long> findMemberIdByChatRoomId(final Long roomId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Participant p WHERE p.chatRoomId = :roomId")
    void deleteByChatRoomId(@Param("roomId") Long roomId);

}
