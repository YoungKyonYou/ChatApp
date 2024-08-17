package com.youyk.anchoreerchat.service.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import com.youyk.anchoreerchat.dto.redis.message.ChatMessageCache;
import com.youyk.anchoreerchat.dto.redis.message.ChatMessageCacheCollection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageCacheServiceImpl implements MessageCacheService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public boolean hasCachedMessages(final Long roomId, final PageRequest pageRequest) {
        final String messageCacheKey = getCacheKey(roomId);
        final String messageHashKey = getHashKey(pageRequest);

        //캐싱이 되어 있는지 확인
        return Boolean.TRUE.equals(redisTemplate.opsForHash().hasKey(messageCacheKey, messageHashKey));
    }

    @Override
    public Slice<ChatMessageDto> retrieveMessagesFromCache(final Long roomId, final PageRequest pageRequest) {
        final String messageCacheKey = getCacheKey(roomId);
        final String messageHashKey = getHashKey(pageRequest);

        final ChatMessageCacheCollection cacheMessages = objectMapper.convertValue(
                redisTemplate.opsForHash().get(messageCacheKey, messageHashKey), ChatMessageCacheCollection.class);

        final List<ChatMessageDto> contents = ChatMessageDto.from(cacheMessages.chatMessageCaches());

        final boolean hasNext = cacheMessages.hasNext();

        //캐싱된 과거 메시지 반환
        return new SliceImpl<>(contents, pageRequest, hasNext);
    }


    @Override
    public void cacheSliceMessages(
            final List<ChatMessageDto> contents,
            final Long roomId,
            final boolean hasNext,
            final PageRequest pageRequest
    ) {
        final String messageCacheKey = getCacheKey(roomId);
        final String messageHashKey = getHashKey(pageRequest);


        final List<ChatMessageCache> messages = ChatMessageCache.of(contents);
        final ChatMessageCacheCollection cacheMessages = ChatMessageCacheCollection.of(messages, hasNext);

        //과거 메시지 캐싱
        redisTemplate.opsForHash().put(messageCacheKey, messageHashKey, cacheMessages);

        // 1시간 TTL 설정
        redisTemplate.expire(messageCacheKey, 1, TimeUnit.HOURS);
    }

    private String getCacheKey(final Long roomId) {
        return "messages:roomId:" + roomId;
    }
    private String getHashKey(final PageRequest pageRequest) {
        return "page:" + pageRequest.getPageNumber() + ":size:" + pageRequest.getPageSize();
    }
}
