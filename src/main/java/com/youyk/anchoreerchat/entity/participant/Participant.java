package com.youyk.anchoreerchat.entity.participant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Participant {
    @GeneratedValue
    @Id
    Long participantId;

    Long chatRoomId;
    Long memberId;
    LocalDateTime joinedDateTime;
}
