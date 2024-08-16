package com.youyk.anchoreerchat.service.member;

import com.youyk.anchoreerchat.entity.member.Member;
import com.youyk.anchoreerchat.repository.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
    @InjectMocks
    private MemberServiceImpl memberService;
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private Clock clock;

    @Test
    void 삼십분_내_접속자_수를_정상적으로_반환합니다() {
        //given
        Instant now = Instant.now();
        given(clock.instant()).willReturn(now);
        given(clock.getZone()).willReturn(ZoneId.systemDefault());
        LocalDateTime thirtyMinutesAgo = LocalDateTime.now(clock).minusMinutes(30);

        given(memberRepository.findMemberByLoginDateTimeWithIn30Minutes(thirtyMinutesAgo)).willReturn(6);
        //when
        final Integer memberCountByLoginDateTimeWithin30Minutes = memberService.findMemberCountByLoginDateTimeWithin30Minutes().data();
        verify(memberRepository).findMemberByLoginDateTimeWithIn30Minutes(thirtyMinutesAgo);

        //then
        Assertions.assertEquals(6, memberCountByLoginDateTimeWithin30Minutes);
    }

    @Test
    void 사용자_아이디로_사용자를_정상적으로_반환합니다() {
        //given
        final String name = "Tom";
        final Member member = Member.builder().name(name).build();
        given(memberRepository.findById(member.getMemberId())).willReturn(java.util.Optional.of(member));

        //when
        Member save = memberRepository.save(member);
        final Member foundMember = memberService.findById(member.getMemberId());

        //then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(foundMember),
                () -> Assertions.assertEquals(member.getMemberId(), foundMember.getMemberId()),
                () -> Assertions.assertEquals(name, foundMember.getName())
        );
    }

}