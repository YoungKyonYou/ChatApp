package com.youyk.anchoreerchat.service.participant;

import com.youyk.anchoreerchat.common.error.exception.DomainExceptionCode;
import com.youyk.anchoreerchat.entity.participant.Participant;
import com.youyk.anchoreerchat.repository.participant.ParticipantRepository;
import com.youyk.anchoreerchat.service.cache.participant.ParticipantCacheService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ParticipantServiceImpl implements ParticipantService{
    private final ParticipantRepository participantRepository;
    private final ParticipantCacheService participantCacheService;

    @Override
    public void addParticipantInCache(final Long roomId) {
        final List<Long> memberIds = participantRepository.findMemberIdByChatRoomId(roomId);

        if(memberIds.isEmpty()){
            throw DomainExceptionCode.PARTICIPANT_NOT_FOUND.newInstance("room id :"+roomId);
        }
        participantCacheService.addParticipant(roomId, memberIds);
    }

    @Override
    public void removeParticipantInCache(final Long roomId) {
        participantCacheService.removeParticipant(roomId);
    }

    @Override
    public boolean isParticipant(Long chatRoomId, Long senderId) {
        return participantCacheService.hasParticipant(chatRoomId, senderId);
    }
}
