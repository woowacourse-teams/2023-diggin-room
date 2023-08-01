package com.digginroom.digginroom.controller;

import static com.digginroom.digginroom.controller.TestFixture.MEMBER_LOGIN_REQUEST;
import static com.digginroom.digginroom.controller.TestFixture.파워;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.digginroom.digginroom.controller.dto.RoomRequest;
import com.digginroom.digginroom.domain.MediaSource;
import com.digginroom.digginroom.domain.Room;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.repository.RoomRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SuppressWarnings("NonAsciiCharacters")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoomControllerTest extends ControllerTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RoomRepository roomRepository;
    private Room room1;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        memberRepository.save(파워());
        room1 = roomRepository.save(new Room(new MediaSource("room1")));
        roomRepository.save(new Room(new MediaSource("room2")));
    }

    @Test
    void 로그인된_사용자는_룸을_탐색할_수_있다() {
        Response response = RestAssured.given().log().all()
                .body(MEMBER_LOGIN_REQUEST)
                .contentType(ContentType.JSON)
                .when()
                .post("/login");

        String cookie = response.header("Set-Cookie");

        RestAssured.given()
                .cookie(cookie)
                .when()
                .get("/room")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("roomId", notNullValue(Long.class))
                .body("isScrapped", equalTo(false));
    }

    @Test
    void 로그인하지_않은_사용자는_룸을_탐색할_수_없다() {
        RestAssured.given()
                .when()
                .get("/room")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 룸을_스크랩할_수_있다() {
        Response response = RestAssured.given().log().all()
                .body(MEMBER_LOGIN_REQUEST)
                .contentType(ContentType.JSON)
                .when()
                .post("/login");

        String cookie = response.header("Set-Cookie");

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/scrap")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 룸_스크랩을_취소할_수_있다() {
        Response response = RestAssured.given().log().all()
                .body(MEMBER_LOGIN_REQUEST)
                .contentType(ContentType.JSON)
                .when()
                .post("/login");

        String cookie = response.header("Set-Cookie");

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/scrap")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .delete("/scrap")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
