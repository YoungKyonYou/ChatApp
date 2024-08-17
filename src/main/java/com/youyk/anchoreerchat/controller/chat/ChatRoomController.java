package com.youyk.anchoreerchat.controller.chat;

import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;
import com.youyk.anchoreerchat.entity.participant.Participant;
import com.youyk.anchoreerchat.request.chat.ChatRoomRequest;
import com.youyk.anchoreerchat.service.chat.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/chats")
@RequiredArgsConstructor
@RestController
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    @Operation(summary = "채팅방 생성 API", description = "요청된 채팅방 이름으로 채팅방 생성")
    @ApiResponse(responseCode = "201", description = "성공 시 Created Response 반환",content = @Content)
    @PostMapping("/rooms")
    public ResponseEntity<Void> createChatRoom(@RequestBody @Valid ChatRoomRequest request) {
        chatRoomService.createChatRoom(request.roomName(), request.memberIds());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "채팅 목록 반환 API", description = "30분 내에 접속한 사용자 수의 내림차순으로 채팅 목록 정렬")
    @GetMapping("/rooms/recently-active")
    public ResponseEntity<DataResponse<List<ChatRoomDto>>> getChatRoomByRecentLoginMember() {
        return ResponseEntity.status(HttpStatus.OK).body(chatRoomService.getChatRoomByRecentLoginMember());
    }

    @Operation(summary = "채팅방에 참여자 조회 API", description = "특정 채팅방에 참여자 목록 조회")
    @GetMapping("/rooms/{roomId}/participants")
    public ResponseEntity<DataResponse<List<Participant>>> getChatRoomParticipants(@PathVariable Long roomId) {
        return ResponseEntity.ok(chatRoomService.getChatRoomParticipants(roomId));
    }
}
