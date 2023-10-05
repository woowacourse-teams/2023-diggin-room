package com.digginroom.digginroom.feedback.controller;

import com.digginroom.digginroom.TestFixture;
import com.digginroom.digginroom.controller.ControllerTest;
import com.digginroom.digginroom.feedback.dto.FeedbackRequest;
import com.digginroom.digginroom.feedback.dto.FeedbackResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.digginroom.digginroom.TestFixture.MEMBER_LOGIN_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FeedbackControllerTest extends ControllerTest {

    private String cookie;

    @BeforeEach
    void login() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(TestFixture.MEMBER_SAVE_REQUEST)
                .when().post("/join")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        Response response = RestAssured.given().log().all()
                .body(MEMBER_LOGIN_REQUEST)
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().response();

        this.cookie = response.header("Set-Cookie");
    }

    @Test
    void 피드백을_받는다() throws JsonProcessingException {
        String content = "피드백 창구좀 만들어주세용";

        RestAssured.given().log().all()
                .body(new FeedbackRequest(content))
                .contentType(ContentType.JSON)
                .cookie(cookie)
                .when()
                .post("/feedbacks")
                .then()
                .statusCode(HttpStatus.OK.value());

        String body = RestAssured.given()
                .when()
                .get("/feedbacks")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().extract().body().asString();
        assertThat(body).isEqualTo(new ObjectMapper().writeValueAsString(
                List.of(new FeedbackResponse(content))
        ));
    }
}
