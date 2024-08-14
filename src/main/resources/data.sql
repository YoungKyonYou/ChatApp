/*CREATE TABLE chat_room (
                           room_id BIGINT NOT NULL,
                           room_name VARCHAR(255),
                           PRIMARY KEY (room_id)
);
CREATE TABLE chat_room_member (
                                  chat_room_member_id BIGINT NOT NULL,
                                  chat_room_id BIGINT,
                                  created_at TIMESTAMP,
                                  sender_id BIGINT,
                                  message_id BIGINT,
                                  PRIMARY KEY (chat_room_member_id)
);

CREATE TABLE member (
                        member_id BIGINT NOT NULL,
                        login_date_time TIMESTAMP,
                        name VARCHAR(255),
                        PRIMARY KEY (member_id)
);

CREATE TABLE message (
                         message_id BIGINT NOT NULL,
                         content VARCHAR(255),
                         created_at TIMESTAMP,
                         updated_at TIMESTAMP,
                         PRIMARY KEY (message_id)
);

CREATE TABLE participant (
                             participant_id BIGINT NOT NULL,
                             chat_room_id BIGINT,
                             joined_date_time TIMESTAMP,
                             member_id BIGINT,
                             PRIMARY KEY (participant_id)
);*/

INSERT INTO chat_room (room_id, room_name) VALUES (1, 'General');
INSERT INTO chat_room (room_id, room_name) VALUES (2, 'Development');
INSERT INTO chat_room (room_id, room_name) VALUES (3, 'Marketing');


INSERT INTO message (message_id, content, created_at, updated_at) VALUES (1, 'Hello, everyone!', '2024-08-14 10:00:00', '2024-08-14 10:00:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (2, 'Good morning!', '2024-08-14 10:05:00', '2024-08-14 10:05:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (3, 'Meeting at 3 PM.', '2024-08-14 10:10:00', '2024-08-14 10:10:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (4, 'Project deadline is tomorrow.', '2024-08-14 10:15:00', '2024-08-14 10:15:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (5, 'Lunch at 12?', '2024-08-14 10:20:00', '2024-08-14 10:20:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (6, 'Please review the document.', '2024-08-14 10:25:00', '2024-08-14 10:25:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (7, 'The server is down.', '2024-08-14 10:30:00', '2024-08-14 10:30:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (8, 'Call me when you are free.', '2024-08-14 10:35:00', '2024-08-14 10:35:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (9, 'Good job on the presentation!', '2024-08-14 10:40:00', '2024-08-14 10:40:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (10, 'Client meeting at 2 PM.', '2024-08-14 10:45:00', '2024-08-14 10:45:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (11, 'The report is finalized.', '2024-08-14 10:50:00', '2024-08-14 10:50:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (12, 'Let’s take a short break.', '2024-08-14 10:55:00', '2024-08-14 10:55:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (13, 'Please update the software.', '2024-08-14 11:00:00', '2024-08-14 11:00:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (14, 'Team meeting in 5 minutes.', '2024-08-14 11:05:00', '2024-08-14 11:05:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (15, 'The issue has been resolved.', '2024-08-14 11:10:00', '2024-08-14 11:10:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (16, 'The new design looks great.', '2024-08-14 11:15:00', '2024-08-14 11:15:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (17, 'We need to discuss the budget.', '2024-08-14 11:20:00', '2024-08-14 11:20:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (18, 'The proposal has been sent.', '2024-08-14 11:25:00', '2024-08-14 11:25:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (19, 'The meeting has been rescheduled.', '2024-08-14 11:30:00', '2024-08-14 11:30:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (20, 'Let’s meet in the conference room.', '2024-08-14 11:35:00', '2024-08-14 11:35:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (21, 'The task is completed.', '2024-08-14 11:40:00', '2024-08-14 11:40:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (22, 'We need to hire more developers.', '2024-08-14 11:45:00', '2024-08-14 11:45:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (23, 'Can you review this code?', '2024-08-14 11:50:00', '2024-08-14 11:50:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (24, 'There’s a bug in the system.', '2024-08-14 11:55:00', '2024-08-14 11:55:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (25, 'The document is ready for review.', '2024-08-14 12:00:00', '2024-08-14 12:00:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (26, 'Let’s wrap up for today.', '2024-08-14 12:05:00', '2024-08-14 12:05:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (27, 'The deadline is approaching.', '2024-08-14 12:10:00', '2024-08-14 12:10:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (28, 'Can we reschedule the call?', '2024-08-14 12:15:00', '2024-08-14 12:15:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (29, 'The server maintenance is complete.', '2024-08-14 12:20:00', '2024-08-14 12:20:00');
INSERT INTO message (message_id, content, created_at, updated_at) VALUES (30, 'Please approve the changes.', '2024-08-14 12:25:00', '2024-08-14 12:25:00');


INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (1, 1, '2024-08-14 10:00:00', 1, 1);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (2, 1, '2024-08-14 10:05:00', 2, 2);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (3, 1, '2024-08-14 10:10:00', 3, 3);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (4, 1, '2024-08-14 10:00:00', 1, 4);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (5, 1, '2024-08-14 10:05:00', 2, 5);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (6, 1, '2024-08-14 10:10:00', 3, 6);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (7, 1, '2024-08-14 10:00:00', 1, 7);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (8, 1, '2024-08-14 10:05:00', 2, 8);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (9, 1, '2024-08-14 10:10:00', 3, 9);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (10, 1, '2024-08-14 10:00:00', 1, 10);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (11, 1, '2024-08-14 10:05:00', 2, 11);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (12, 1, '2024-08-14 10:10:00', 3, 12);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (13, 1, '2024-08-14 10:00:00', 1, 13);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (14, 1, '2024-08-14 10:05:00', 2, 14);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (15, 1, '2024-08-14 10:10:00', 3, 15);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (16, 1, '2024-08-14 10:00:00', 1, 16);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (17, 1, '2024-08-14 10:05:00', 2, 17);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (18, 1, '2024-08-14 10:10:00', 3, 18);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (19, 1, '2024-08-14 10:00:00', 1, 19);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (20, 1, '2024-08-14 10:05:00', 2, 20);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (21, 1, '2024-08-14 10:10:00', 3, 21);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (22, 1, '2024-08-14 10:00:00', 1, 22);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (23, 1, '2024-08-14 10:05:00', 2, 23);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (24, 1, '2024-08-14 10:10:00', 3, 24);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (25, 1, '2024-08-14 10:00:00', 1, 25);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (26, 1, '2024-08-14 10:05:00', 2, 26);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (27, 1, '2024-08-14 10:10:00', 3, 27);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (28, 1, '2024-08-14 10:00:00', 1, 28);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (29, 1, '2024-08-14 10:05:00', 2, 29);
INSERT INTO chat_room_member (chat_room_member_id, chat_room_id, created_at, sender_id, message_id)
VALUES (30, 1, '2024-08-14 10:10:00', 3, 30);


INSERT INTO member (member_id, login_date_time, name) VALUES (1, DATEADD(MINUTE, -60, CURRENT_TIMESTAMP), 'Alice');
INSERT INTO member (member_id, login_date_time, name) VALUES (2, DATEADD(MINUTE, -50, CURRENT_TIMESTAMP), 'Bob');
INSERT INTO member (member_id, login_date_time, name) VALUES (3, DATEADD(MINUTE, -40, CURRENT_TIMESTAMP), 'Sam');
INSERT INTO member (member_id, login_date_time, name) VALUES (4, DATEADD(MINUTE, -35, CURRENT_TIMESTAMP), 'Charlie');
INSERT INTO member (member_id, login_date_time, name) VALUES (5, DATEADD(MINUTE, -50, CURRENT_TIMESTAMP), 'Young');
INSERT INTO member (member_id, login_date_time, name) VALUES (6, DATEADD(MINUTE, -40, CURRENT_TIMESTAMP), 'Mat');
INSERT INTO member (member_id, login_date_time, name) VALUES (7, DATEADD(MINUTE, -20, CURRENT_TIMESTAMP), 'David');
INSERT INTO member (member_id, login_date_time, name) VALUES (8, DATEADD(MINUTE, -10, CURRENT_TIMESTAMP), 'Tom');
INSERT INTO member (member_id, login_date_time, name) VALUES (9, DATEADD(MINUTE, -6, CURRENT_TIMESTAMP), 'Jerry');
INSERT INTO member (member_id, login_date_time, name) VALUES (10,DATEADD(MINUTE, -5, CURRENT_TIMESTAMP),  'Aron');
INSERT INTO member (member_id, login_date_time, name) VALUES (11,DATEADD(MINUTE, -4, CURRENT_TIMESTAMP),  'Can');
INSERT INTO member (member_id, login_date_time, name) VALUES (12,DATEADD(MINUTE, -3, CURRENT_TIMESTAMP),  'Barby');


INSERT INTO participant (participant_id, chat_room_id, joined_date_time, member_id)
VALUES (1, 1, '2024-08-14 09:30:00', 1);
INSERT INTO participant (participant_id, chat_room_id, joined_date_time, member_id)
VALUES (2, 1, '2024-08-14 09:35:00', 2);
INSERT INTO participant (participant_id, chat_room_id, joined_date_time, member_id)
VALUES (3, 1, '2024-08-14 09:40:00', 3);
INSERT INTO participant (participant_id, chat_room_id, joined_date_time, member_id)
VALUES (4, 1, '2024-08-14 09:40:00', 4);
INSERT INTO participant (participant_id, chat_room_id, joined_date_time, member_id)
VALUES (5, 2, '2024-08-14 09:40:00', 5);
INSERT INTO participant (participant_id, chat_room_id, joined_date_time, member_id)
VALUES (6, 2, '2024-08-14 09:40:00', 6);
INSERT INTO participant (participant_id, chat_room_id, joined_date_time, member_id)
VALUES (7, 2, '2024-08-14 09:40:00', 7);
INSERT INTO participant (participant_id, chat_room_id, joined_date_time, member_id)
VALUES (8, 2, '2024-08-14 09:40:00', 8);
INSERT INTO participant (participant_id, chat_room_id, joined_date_time, member_id)
VALUES (9, 3, '2024-08-14 09:40:00', 9);
INSERT INTO participant (participant_id, chat_room_id, joined_date_time, member_id)
VALUES (10, 3, '2024-08-14 09:40:00', 10);
INSERT INTO participant (participant_id, chat_room_id, joined_date_time, member_id)
VALUES (11, 3, '2024-08-14 09:40:00', 11);
INSERT INTO participant (participant_id, chat_room_id, joined_date_time, member_id)
VALUES (12, 3, '2024-08-14 09:40:00', 12);



DROP SEQUENCE IF EXISTS chat_room_member_seq;
DROP SEQUENCE IF EXISTS chat_room_seq;
DROP SEQUENCE IF EXISTS member_seq;
DROP SEQUENCE IF EXISTS message_seq;
DROP SEQUENCE IF EXISTS participant_seq;

-- 새로운 시퀀스 생성
CREATE SEQUENCE chat_room_member_seq START WITH 81 INCREMENT BY 1;
CREATE SEQUENCE chat_room_seq START WITH 53 INCREMENT BY 1;
CREATE SEQUENCE member_seq START WITH 63 INCREMENT BY 1;
CREATE SEQUENCE message_seq START WITH 81 INCREMENT BY 1;
CREATE SEQUENCE participant_seq START WITH 62 INCREMENT BY 1;