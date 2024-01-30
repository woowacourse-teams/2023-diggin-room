package com.digginroom.digginroom.feedback.controller;

import static com.digginroom.digginroom.TestFixture.MEMBER_LOGIN_REQUEST;
import static com.digginroom.digginroom.TestFixture.파워;
import static org.assertj.core.api.Assertions.assertThat;

import com.digginroom.digginroom.TestFixture;
import com.digginroom.digginroom.controller.ControllerTest;
import com.digginroom.digginroom.controller.mock.MockLoginServer;
import com.digginroom.digginroom.feedback.domain.Feedback;
import com.digginroom.digginroom.feedback.dto.FeedbackRequest;
import com.digginroom.digginroom.feedback.dto.FeedbackResponse;
import com.digginroom.digginroom.feedback.repository.FeedbackRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("auth")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FeedbackControllerTest extends ControllerTest {

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private MockLoginServer mockLoginServer;
    private String cookie;

    @BeforeEach
    void login() {
        this.cookie = mockLoginServer.getCachedLoginValue(port);
    }

    @Test
    void 피드백을_받는다() {
        String content = "피드백 창구좀 만들어주세용";

        RestAssured.given().log().all()
                .body(new FeedbackRequest(content))
                .contentType(ContentType.JSON)
                .cookie(cookie)
                .when()
                .post("/feedbacks")
                .then()
                .statusCode(HttpStatus.OK.value());

        assertThat(feedbackRepository.findAll())
                .usingRecursiveFieldByFieldElementComparatorOnFields("content")
                .containsExactly(new Feedback(파워().getId(), content));
    }

    @Test
    void 피드백을_조회한다() throws JsonProcessingException {
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

        FeedbackResponse[] actual = new ObjectMapper().readerForArrayOf(FeedbackResponse.class).readValue(body);
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("writerId")
                .containsExactly(FeedbackResponse.of(new Feedback(파워().getId(), content)));
    }

    @Test
    void 피드백_문구가_비어있으면_예외가_발생한다() {
        String content = "";

        RestAssured.given().log().all()
                .body(new FeedbackRequest(content))
                .contentType(ContentType.JSON)
                .cookie(cookie)
                .when()
                .post("/feedbacks")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
