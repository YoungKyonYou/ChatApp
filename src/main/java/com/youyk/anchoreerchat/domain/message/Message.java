package com.youyk.anchoreerchat.domain.message;

import com.youyk.anchoreerchat.domain.message.constant.MessageType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Message {
    @GeneratedValue
    @Id
    private Long messageId;
    private String content;
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ElementCollection
    @CollectionTable(name = "message_ids", joinColumns = @JoinColumn(name = "message_id"))
    @Column(name = "related_message_id")
    private List<Long> messageIds;

    private LocalDateTime createdAt;
    private LocalDate updatedAt;
}
