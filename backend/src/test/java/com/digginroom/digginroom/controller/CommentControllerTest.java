package com.digginroom.digginroom.controller;

import static com.digginroom.digginroom.controller.TestFixture.MEMBER_LOGIN_REQUEST;
import static com.digginroom.digginroom.controller.TestFixture.나무;
import static com.digginroom.digginroom.controller.TestFixture.파워;

import com.digginroom.digginroom.controller.dto.CommentResponse;
import com.digginroom.digginroom.domain.Room;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.repository.RoomRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SuppressWarnings("NonAsciiCharacters")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CommentControllerTest extends ControllerTest {

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
        room1 = roomRepository.save(나무());
    }

    @Test
    void 댓글을_작성_할_수_있다() {
        Response response = RestAssured.given().log().all()
                .body(MEMBER_LOGIN_REQUEST)
                .contentType(ContentType.JSON)
                .when()
                .post("/login");

        String cookie = response.header("Set-Cookie");

        RestAssured.given().log().all()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .body(TestFixture.COMMENT_REQUEST)
                .when().post("/rooms/" + room1.getId() + "/comments")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .body("writer", Matchers.equalTo(TestFixture.MEMBER_USERNAME))
                .body("comment", Matchers.equalTo(TestFixture.COMMENT_REQUEST.comment()))
                .body("createdAt", Matchers.notNullValue())
                .body("updatedAt", Matchers.notNullValue())
                .extract().as(CommentResponse.class);
    }
}
