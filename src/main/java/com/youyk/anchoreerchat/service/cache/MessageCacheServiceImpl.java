package com.youyk.anchoreerchat.service.cache;

import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import com.youyk.anchoreerchat.dto.redis.message.ChatMessageCache;
import com.youyk.anchoreerchat.request.page.PageableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class MessageCacheServiceImpl implements MessageCacheService {
    private final RedisTemplate<String, Object> redisTemplate;
    private String messageCacheKey = "messages:roomId:%s:page:%s:size:%s";

    @Override
    public boolean hasCachedMessages(final Long roomId, final PageableRequest pageableRequest) {
        messageCacheKey = String.format(messageCacheKey, roomId, pageableRequest.page(), pageableRequest.size());
        return Boolean.TRUE.equals(redisTemplate.hasKey(messageCacheKey));
    }

    @Override
    public Slice<ChatMessageDto> retrieveMessagesFromCache(final Long roomId, final PageRequest pageRequest) {
        messageCacheKey = String.format(messageCacheKey, roomId, pageRequest.getPageNumber(), pageRequest.getPageSize());

        final List<ChatMessageDto> cachedMessages = Objects.requireNonNull(redisTemplate.opsForZSet().range(messageCacheKey, pageRequest.getPageNumber(), pageRequest.getPageSize())).stream()
                .filter(Objects::nonNull)
                .map((data) -> {
                    ChatMessageCache chatMessageCache = (ChatMessageCache) data;
                    return ChatMessageDto.builder()
                            .content(chatMessageCache.content())
                            .senderId(chatMessageCache.senderId())
                            .senderName(chatMessageCache.senderName())
                            .chatRoomId(chatMessageCache.chatRoomId())
                            .chatRoomName(chatMessageCache.chatRoomName())
                            .createdAt(chatMessageCache.createdAt())
                            .build();
                })
                .toList();

        return new SliceImpl<>(cachedMessages, pageRequest, false);
    }


    @Override
    public void cacheSliceMessages(
            final List<ChatMessageDto> contents,
            final Long roomId,
            final boolean hasNext,
            final PageableRequest pageableRequest
    ) {
        messageCacheKey = String.format(messageCacheKey, roomId, pageableRequest.page(), pageableRequest.size());

        List<ChatMessageCache> chatMessageCaches = ChatMessageCache.of(contents, hasNext);

        chatMessageCaches.forEach(data -> {
                            long epochSeconds = data.createdAt().toInstant(ZoneOffset.UTC).toEpochMilli() / 1000;
                            final double score = -(epochSeconds);
                            redisTemplate.opsForZSet().add(messageCacheKey, data, score);
                        });
    }
}
