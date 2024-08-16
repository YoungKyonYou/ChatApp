package com.youyk.anchoreerchat.e2e.member;

import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberE2ETest {
    @LocalServerPort
    int port;
    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void 최근_접속_사용자_수를_정상적으로_가져옵니다(){
        final Response response = given().log()
                .all()
                .when()
                .contentType(ContentType.APPLICATION_JSON.toString())
                .get("/member/recently-active/count");
        response.then()
                .statusCode(HttpStatus.OK.value())
                .log().all();

        final Integer count = response.jsonPath().get("data");
        Assertions.assertEquals(6, count);
    }
}
