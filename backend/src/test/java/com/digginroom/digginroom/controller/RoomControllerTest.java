package com.digginroom.digginroom.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.digginroom.digginroom.controller.dto.MemberLoginRequest;
import com.digginroom.digginroom.controller.dto.RoomRequest;
import com.digginroom.digginroom.domain.MediaSource;
import com.digginroom.digginroom.domain.Member;
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

    private static final String MEMBER_PASSWORD = "power1234@";
    private static final String MEMBER_USERNAME = "power2";

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RoomRepository roomRepository;
    private Member member;
    private Room room1;
    private Room room2;
    private Room room3;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        member = new Member(MEMBER_USERNAME, MEMBER_PASSWORD);
        memberRepository.save(member);
        room1 = new Room(new MediaSource("room1"));
        room2 = new Room(new MediaSource("room2"));
        room3 = new Room(new MediaSource("room3"));
        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);
    }

    @Test
    void 로그인된_사용자는_룸을_탐색할_수_있다() {
        Response response = RestAssured.given().log().all()
                .body(new MemberLoginRequest(MEMBER_USERNAME, MEMBER_PASSWORD))
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
                .body(new MemberLoginRequest(MEMBER_USERNAME, MEMBER_PASSWORD))
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
                .body(new MemberLoginRequest(MEMBER_USERNAME, MEMBER_PASSWORD))
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
