package com.youyk.anchoreerchat.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Sql(statements = "ALTER TABLE participant ALTER COLUMN participant_id RESTART WITH 1")
@Sql(statements = "ALTER TABLE chat_room ALTER COLUMN room_id RESTART WITH 1")
@Sql(statements = "ALTER TABLE message ALTER COLUMN message_id RESTART WITH 1")
@Sql(statements = "ALTER TABLE chat_room_member ALTER COLUMN chat_room_member_id RESTART WITH 1")
@Sql(statements = "ALTER TABLE member ALTER COLUMN member_id RESTART WITH 1")
@SpringBootTest
@Transactional
@Retention(RetentionPolicy.RUNTIME)
public @interface InitializeSpringBootTest {
}
