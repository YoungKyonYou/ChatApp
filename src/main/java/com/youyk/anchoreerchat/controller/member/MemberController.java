package com.youyk.anchoreerchat.controller.member;

import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/recent-login/count")
    public ResponseEntity<DataResponse> getRecentLoginMemberCount() {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findMemberCountByLoginDateTimeWithin30Minutes());
    }
}
