package com.youyk.anchoreerchat.controller.participant;

import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;
import com.youyk.anchoreerchat.service.participant.ParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/participant")
@RequiredArgsConstructor
@RestController
public class ParticipantController {
    private final ParticipantService participantService;
    @Operation(summary = "채팅 목록 반환 API", description = "30분 내에 접속한 사용자 수의 내림차순으로 채팅 목록 정렬")
    @GetMapping("/recent-login/member/room/list")
    public ResponseEntity<DataResponse<List<ChatRoomDto>>> getChatRoomByRecentLoginMember() {
        return ResponseEntity.status(HttpStatus.OK).body(participantService.getChatRoomByRecentLoginMember());
    }
}
