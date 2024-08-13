package com.youyk.anchoreerchat.service.member;

import com.youyk.anchoreerchat.common.error.exception.DomainExceptionCode;
import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.entity.member.Member;
import com.youyk.anchoreerchat.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public DataResponse findMemberCountByLoginDateTimeWithin30Minutes() {
        final LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
        return DataResponse.from(memberRepository.findMemberByLoginDateTimeWithIn30Minutes(thirtyMinutesAgo));
    }

    @Transactional(readOnly = true)
    @Override
    public Member findById(final Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(()->DomainExceptionCode.MEMBER_NOT_FOUND.newInstance(memberId));
    }
}
