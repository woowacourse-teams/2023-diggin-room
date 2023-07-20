package com.digginroom.digginroom.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.digginroom.digginroom.controller.dto.MemberLoginRequest;
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

@SuppressWarnings("NonAsciiCharacters")
class RoomControllerTest extends ControllerTest {

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
        member = new Member("power", "power1234@");
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
                .body(new MemberLoginRequest(member.getUsername(), member.getPassword()))
                .contentType(ContentType.JSON)
                .when()
                .post("/join/login");

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
}
