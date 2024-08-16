package com.youyk.anchoreerchat.dto.redis.message;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;

public record ChatMessageCacheCollection(
        List<ChatMessageCache> chatMessageCaches,
        boolean hasNext
) implements Serializable {

    public static ChatMessageCacheCollection of(final List<ChatMessageCache> chatMessageCaches, final boolean hasNext){
        return new ChatMessageCacheCollection(chatMessageCaches, hasNext);
    }
}
