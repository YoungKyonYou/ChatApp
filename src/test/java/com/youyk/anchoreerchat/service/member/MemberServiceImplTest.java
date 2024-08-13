package com.youyk.anchoreerchat.service.member;

import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.entity.member.Member;
import com.youyk.anchoreerchat.repository.member.MemberRepository;
import com.youyk.anchoreerchat.util.InitializeSpringBootTest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@InitializeSpringBootTest
class MemberServiceImplTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;


    @Test
    void 삼십분_내_접속자_수를_정상적으로_반환합니다() {
        //given
        final String[] names = {"Tom", "Sam", "John", "Young", "Sung", "Bill"};
        final Integer[] minutes = {0, 10, 20, 30, 40, 50};

        List<Member> members = new ArrayList<>();

        IntStream.range(0, 6).forEach(i -> {
            members.add(Member.builder().name(names[i]).loginDateTime(LocalDateTime.now().minusMinutes(minutes[i]))
                    .build());
        });

        memberRepository.saveAll(members);

        //when
        DataResponse<Integer> memberCountByLoginDateTimeWithin30Minutes = memberService.findMemberCountByLoginDateTimeWithin30Minutes();
        Integer memberCount = memberCountByLoginDateTimeWithin30Minutes.data();

        //then
        Assertions.assertEquals(3, memberCount);
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