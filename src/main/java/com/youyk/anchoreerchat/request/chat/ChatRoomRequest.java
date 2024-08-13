package com.youyk.anchoreerchat.request.chat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ChatRoomRequest(
        @Size(min = 1, max = 100)
        String roomName,
        @NotNull
        List<Long> memberIds
) {
}
