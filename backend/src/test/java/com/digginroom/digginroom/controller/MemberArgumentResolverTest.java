package com.digginroom.digginroom.controller;

import static org.hamcrest.Matchers.equalTo;

import com.digginroom.digginroom.service.dto.ErrorResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
class MemberArgumentResolverTest extends ControllerTest {

    @Test
    void 세션_정보가_없는_경우_예외를_던진다() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .get("/member/genres")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .body("timeStamp", Matchers.notNullValue())
                .body("errorMessage", equalTo("회원 인증 정보가 존재하지 않습니다."))
                .extract().as(ErrorResponse.class);
    }

    @Test
    void 세션_정보가_잘못된_경우_예외를_던진다() {
        RestAssured.given().log().all()
                .cookie("JSESSIONID", "anything")
                .contentType(ContentType.JSON)
                .when()
                .get("/member/genres")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .body("timeStamp", Matchers.notNullValue())
                .body("errorMessage", equalTo("회원 인증 정보가 존재하지 않습니다."))
                .extract().as(ErrorResponse.class);
    }
}
