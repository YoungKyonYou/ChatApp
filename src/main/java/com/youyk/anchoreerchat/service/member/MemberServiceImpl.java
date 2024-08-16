package com.youyk.anchoreerchat.service.member;

import com.youyk.anchoreerchat.common.error.exception.DomainExceptionCode;
import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.entity.member.Member;
import com.youyk.anchoreerchat.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final Clock clock;

    @Transactional(readOnly = true)
    @Override
    public DataResponse<Integer> findMemberCountByLoginDateTimeWithin30Minutes() {
        //현재 시간으로부터 30분 이전 시간
        final LocalDateTime thirtyMinutesAgo = LocalDateTime.now(clock).minusMinutes(30);
        //30분 이내 로그인한 회원 수 반환
        return DataResponse.from(memberRepository.findMemberByLoginDateTimeWithIn30Minutes(thirtyMinutesAgo));
    }

    @Transactional(readOnly = true)
    @Override
    public Member findById(final Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(()->DomainExceptionCode.MEMBER_NOT_FOUND.newInstance(memberId));
    }
}
