package com.youyk.anchoreerchat.service.cache.message;

import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

public interface MessageCacheService {
    Slice<ChatMessageDto> retrieveMessagesFromCache(final Long roomId, final PageRequest pageRequest);

    boolean hasCachedMessages(final Long roomId, final PageRequest pageRequest);

    void cacheSliceMessages(final List<ChatMessageDto> contents, final Long roomId, final boolean hasNext,
                            final PageRequest pageRequest);
}
