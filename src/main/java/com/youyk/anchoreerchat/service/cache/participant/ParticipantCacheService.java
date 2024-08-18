package com.youyk.anchoreerchat.service.cache.participant;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class ParticipantCacheService implements ParticipantCacheServiceImpl{
    private final RedisTemplate<String, Object> redisTemplate;
    @Override
    public void addParticipant(final Long roomId, final List<Long> participantIds) {
        final String participantCacheKey = getCacheKey(roomId);

        participantIds.forEach(participantId -> {
            final String participantHashKey = getHashKey(participantId);
            redisTemplate.opsForHash().put(participantCacheKey, participantHashKey, participantId);
        });
    }

    @Override
    public void removeParticipant(final Long roomId) {
        redisTemplate.opsForHash().delete(getCacheKey(roomId));
    }

    @Override
    public boolean hasParticipant(final Long chatRoomId, final Long senderId) {
        return redisTemplate.opsForHash().hasKey(getCacheKey(chatRoomId), getHashKey(senderId));
    }

    private String getCacheKey(final Long roomId) {
        return "participant:roomId:" + roomId;
    }
    private String getHashKey(final Long participantId) {
        return "participant:"+participantId;
    }


}
