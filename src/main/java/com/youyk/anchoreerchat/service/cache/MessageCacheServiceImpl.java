package com.youyk.anchoreerchat.service.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import com.youyk.anchoreerchat.dto.redis.message.ChatMessageCache;
import com.youyk.anchoreerchat.dto.redis.message.ChatMessageCacheCollection;
import java.util.List;
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
        String messageCacheKey = "messages:roomId:%s";
        String messageHashKey = "page:%s:size:%s";

        messageCacheKey = String.format(messageCacheKey, roomId);
        messageHashKey = String.format(messageHashKey, pageRequest.getPageNumber(), pageRequest.getPageSize());

        return Boolean.TRUE.equals(redisTemplate.opsForHash().hasKey(messageCacheKey, messageHashKey));
    }

    @Override
    public Slice<ChatMessageDto> retrieveMessagesFromCache(final Long roomId, final PageRequest pageRequest) {
        String messageCacheKey = "messages:roomId:%s";
        String messageHashKey = "page:%s:size:%s";

        messageCacheKey = String.format(messageCacheKey, roomId);
        messageHashKey = String.format(messageHashKey, pageRequest.getPageNumber(), pageRequest.getPageSize());

        final ChatMessageCacheCollection cacheMessages = objectMapper.convertValue(
                redisTemplate.opsForHash().get(messageCacheKey, messageHashKey), ChatMessageCacheCollection.class);

        final List<ChatMessageDto> contents = ChatMessageDto.from(cacheMessages.chatMessageCaches());

        final boolean hasNext = cacheMessages.hasNext();

        return new SliceImpl<>(contents, pageRequest, hasNext);
    }


    @Override
    public void cacheSliceMessages(
            final List<ChatMessageDto> contents,
            final Long roomId,
            final boolean hasNext,
            final PageRequest pageRequest
    ) {
        String messageCacheKey = "messages:roomId:%s";
        String messageHashKey = "page:%s:size:%s";

        messageCacheKey = String.format(messageCacheKey, roomId);
        messageHashKey = String.format(messageHashKey, pageRequest.getPageNumber(), pageRequest.getPageSize());

        final List<ChatMessageCache> messages = ChatMessageCache.of(contents);
        final ChatMessageCacheCollection cacheMessages = ChatMessageCacheCollection.of(messages, hasNext);

        redisTemplate.opsForHash().put(messageCacheKey, messageHashKey, cacheMessages);
    }
}
