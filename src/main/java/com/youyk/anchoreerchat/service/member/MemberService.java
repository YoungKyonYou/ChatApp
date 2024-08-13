package com.youyk.anchoreerchat.service.member;

import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.entity.member.Member;

public interface MemberService {
    DataResponse findMemberCountByLoginDateTimeWithin30Minutes();
    Member findById(final Long id);
}
