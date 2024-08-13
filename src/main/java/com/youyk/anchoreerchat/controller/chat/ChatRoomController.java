package com.youyk.anchoreerchat.controller.chat;

import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.request.chat.ChatRoomRequest;
import com.youyk.anchoreerchat.service.chat.ChatRoomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/chat")
@RequiredArgsConstructor
@RestController
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    @Operation(summary = "채팅방 생성 API", description = "요청된 채팅방 이름으로 채팅방 생성")
    @ApiResponse(responseCode = "201", description = "성공 시 Created Response 반환",content = @Content)
    @PostMapping("/room")
    public ResponseEntity<Void> createChatRoom(@RequestBody @Valid ChatRoomRequest request) {
        chatRoomService.createChatRoom(request.roomName(), request.memberIds());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
