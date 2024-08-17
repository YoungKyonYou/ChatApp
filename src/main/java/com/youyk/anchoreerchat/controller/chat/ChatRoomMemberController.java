package com.youyk.anchoreerchat.controller.chat;

import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import com.youyk.anchoreerchat.request.page.PageableRequest;
import com.youyk.anchoreerchat.service.chat.ChatRoomMemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/chats")
@RestController
public class ChatRoomMemberController {
    private final ChatRoomMemberService chatRoomMemberService;


    @Operation(summary = "과거 채팅 내역 반환 API", description = "과거 채팅 내역을 무한 스크롤 형식으로 반환 - Slice 사용")
    @GetMapping("/rooms/{roomId}/past-messages")
    public ResponseEntity<DataResponse<Slice<ChatMessageDto>>> getPastChatRoomMessages(@ModelAttribute @Valid PageableRequest pageableRequest, @PathVariable @NotNull Long roomId) {
        return ResponseEntity.ok(chatRoomMemberService.getChatRoomMessages(pageableRequest, roomId));
    }
}
