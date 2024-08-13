package com.youyk.anchoreerchat.repository.member;

import com.youyk.anchoreerchat.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT COUNT(*) FROM Member m WHERE m.loginDateTime >= :thirtyMinutesAgo")
    int findMemberByLoginDateTimeWithIn30Minutes(@Param("thirtyMinutesAgo") LocalDateTime thirtyMinutesAgo);
}
