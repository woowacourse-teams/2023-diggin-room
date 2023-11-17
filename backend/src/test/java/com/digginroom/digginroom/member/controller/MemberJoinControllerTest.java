package com.digginroom.digginroom.member.controller;

import static org.hamcrest.Matchers.equalTo;

import com.digginroom.digginroom.TestFixture;
import com.digginroom.digginroom.ControllerTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
class MemberJoinControllerTest extends ControllerTest {

    @Test
    void 회원가입을_할_수_있다() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(TestFixture.MEMBER_SAVE_REQUEST)
                .when().post("/join")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 아이디_중복_여부를_알_수_있다_중복이_아닌_경우() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/join/exist?username=power")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("isDuplicated", equalTo(false));
    }

    @Test
    void 아이디_중복_여부를_알_수_있다_중복인_경우() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(TestFixture.MEMBER_SAVE_REQUEST)
                .when().post("/join")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/join/exist?username=power")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("isDuplicated", equalTo(true));
    }
}
