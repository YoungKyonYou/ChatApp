package com.youyk.anchoreerchat.service.cache.participant;


import java.util.List;

public interface ParticipantCacheServiceImpl {
    void addParticipant(final Long roomId, final List<Long> participantIds);

    void removeParticipant(final Long roomId);

    boolean hasParticipant(final Long chatRoomId, final Long senderId);
}
