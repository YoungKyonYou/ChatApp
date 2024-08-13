package com.youyk.anchoreerchat.repository.message;

import com.youyk.anchoreerchat.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
