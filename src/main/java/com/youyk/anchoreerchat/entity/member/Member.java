package com.youyk.anchoreerchat.entity.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MEMBER")
@Entity
public class Member {
    @GeneratedValue
    @Id
    private Long memberId;

    private String name;

    private LocalDateTime loginDateTime;
}
