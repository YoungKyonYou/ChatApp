package com.youyk.anchoreerchat.service.participant;

public interface ParticipantService {
    void addParticipantInCache(final Long roomId);
    void removeParticipantInCache(final Long roomId);

    boolean isParticipant(final Long chatRoomId, final Long senderId);
}
