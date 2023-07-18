package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.controller.dto.MemberSaveRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
class MemberControllerTest extends ControllerTest {

    @Test
    void 회원가입을_할_수_있다() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new MemberSaveRequest("power", "power"))
                .when().post("/join")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}
