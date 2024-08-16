package com.youyk.anchoreerchat.e2e.chat;

import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatRoomMemberE2ETest {
    @LocalServerPort
    int port;
    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void 과거_채팅_내역이_10개씩_정상적으로_불러와집니다() {
        final Response response = given().log()
                .all()
                .when()
                .contentType(ContentType.APPLICATION_JSON.toString())
                .get("/chats/rooms/1/past-messages?page=0&size=10");
        response.then()
                .statusCode(HttpStatus.OK.value())
                .log().all();

        List<ChatMessageDto> data = response.jsonPath().getList("data.content", ChatMessageDto.class);
        Assertions.assertEquals(10, data.size());
    }
}
