package com.youyk.anchoreerchat.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Member {
    @GeneratedValue
    @Id
    private Long memberId;

    private String name;

    private LocalDateTime loginDateTime;
}
