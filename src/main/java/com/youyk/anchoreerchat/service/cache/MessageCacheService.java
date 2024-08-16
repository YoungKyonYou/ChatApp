package com.youyk.anchoreerchat.service.cache;

import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import com.youyk.anchoreerchat.request.page.PageableRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface MessageCacheService {
    Slice<ChatMessageDto> retrieveMessagesFromCache(final Long roomId, final PageRequest pageRequest);
    boolean hasCachedMessages(final Long roomId, final PageableRequest pageableRequest);
    void cacheSliceMessages(final List<ChatMessageDto> contents, final Long roomId, final boolean hasNext, final PageableRequest pageableRequest);
}
