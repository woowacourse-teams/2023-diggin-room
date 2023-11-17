package com.digginroom.digginroom.exception;

import static org.hamcrest.Matchers.equalTo;

import com.digginroom.digginroom.ControllerTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
class GlobalExceptionHandlerTest extends ControllerTest {

    @Test
    void 핸들러를_찾을_수_없는경우_404_커스텀_에러를_반환한다() {
        RestAssured.given()
                .when()
                .get("/" + Long.MAX_VALUE)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("timeStamp", Matchers.notNullValue())
                .body("errorMessage", equalTo("리소스를 찾을 수 없습니다."));

    }
}
