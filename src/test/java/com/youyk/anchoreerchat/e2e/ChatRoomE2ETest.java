package com.youyk.anchoreerchat.e2e;

import static io.restassured.RestAssured.given;

import com.youyk.anchoreerchat.common.error.exception.DomainExceptionCode;
import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;
import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import com.youyk.anchoreerchat.entity.participant.Participant;
import com.youyk.anchoreerchat.repository.chat.ChatRoomRepository;
import com.youyk.anchoreerchat.repository.participant.ParticipantRepository;
import com.youyk.anchoreerchat.request.chat.ChatRoomRequest;
import com.youyk.anchoreerchat.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.List;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ChatRoomE2ETest {
    @LocalServerPort
    int port;

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;
    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void E2E_30분_내에_접속한_사용자_수의_따라_채팅_목록을_정상적으로_반환합니다(){
        final Response response = given().log()
                .all()
                .when()
                .contentType(ContentType.APPLICATION_JSON.toString())
                .get("/chats/room/recently-active");
        response.then()
                .statusCode(HttpStatus.OK.value())
                .log().all();

        final List<ChatRoomDto> data = response.jsonPath().getList("data", ChatRoomDto.class);

        Assertions.assertArrayEquals(
                new Long[]{3L, 2L, 1L},
                data.stream().map(ChatRoomDto::roomId).toArray());
    }

    @Test
    void E2E_채팅방이_정상적으로_생성됩니다(){
        final ChatRoomRequest request = ChatRoomRequest.builder()
                .roomName("Anchoreer")
                .memberIds(List.of(1L, 2L))
                .build();

        final Response response = given().log()
                .all()
                .when()
                .contentType(ContentType.APPLICATION_JSON.toString())
                .body(request)
                .post("/chats/rooms");
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .log().all();

        final ChatRoom anchoreerRoom = chatRoomRepository.findByRoomName("Anchoreer").orElseThrow(()->
                DomainExceptionCode.CHAT_ROOM_NOT_FOUND.newInstance("Anchoreer"));

        final List<Participant> participants = participantRepository.findByChatRoomId(anchoreerRoom.getRoomId());

        Assertions.assertArrayEquals(
                new Long[]{1L, 2L},
                participants.stream().map(Participant::getMemberId).toArray());
    }

}
