package com.youyk.anchoreerchat.service.member;

import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.entity.member.Member;
import com.youyk.anchoreerchat.repository.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MemberServiceImplTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @Test
    void 삼십분_내_접속자_수를_정상적으로_반환합니다() {
        //when
        DataResponse<Integer> memberCountByLoginDateTimeWithin30Minutes = memberService.findMemberCountByLoginDateTimeWithin30Minutes();
        Integer memberCount = memberCountByLoginDateTimeWithin30Minutes.data();

        //then
        Assertions.assertEquals(6, memberCount);
    }

    @Test
    void 사용자_아이디로_사용자를_정상적으로_반환합니다() {
        //given
        final String name = "Tom";
        final Member member = Member.builder().name(name).build();
        memberRepository.save(member);

        //when
        Member foundMember = memberService.findById(member.getMemberId());

        //then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(foundMember),
                () -> Assertions.assertEquals(member.getMemberId(), foundMember.getMemberId()),
                () -> Assertions.assertEquals(name, foundMember.getName())
        );
    }

}