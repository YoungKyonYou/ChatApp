package com.youyk.anchoreerchat.repository.member;

import com.youyk.anchoreerchat.config.QueryDSLConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

@Import(QueryDSLConfig.class)
@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 최근_접속한_사용자_수를_정상적으로_가져옵니다(){
        //given
        LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
        //when
        int memberByLoginDateTimeWithIn30Minutes = memberRepository.findMemberByLoginDateTimeWithIn30Minutes(thirtyMinutesAgo);
        //then
        Assertions.assertEquals(6, memberByLoginDateTimeWithIn30Minutes);
    }
}
