package com.youyk.anchoreerchat.entity.participant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "PARTICIPANT")
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
