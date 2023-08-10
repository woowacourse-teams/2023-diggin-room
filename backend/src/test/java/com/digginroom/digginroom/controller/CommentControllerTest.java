package com.digginroom.digginroom.controller;

import static com.digginroom.digginroom.controller.TestFixture.MEMBER2_LOGIN_REQUEST;
import static com.digginroom.digginroom.controller.TestFixture.MEMBER_LOGIN_REQUEST;
import static com.digginroom.digginroom.controller.TestFixture.나무;
import static com.digginroom.digginroom.controller.TestFixture.블랙캣;
import static com.digginroom.digginroom.controller.TestFixture.파워;

import com.digginroom.digginroom.controller.dto.CommentRequest;
import com.digginroom.digginroom.controller.dto.CommentResponse;
import com.digginroom.digginroom.controller.dto.MemberLoginRequest;
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
        memberRepository.save(블랙캣());
        room1 = roomRepository.save(나무());
    }

    @Test
    void 댓글을_작성_할_수_있다() {
        String cookie = login(MEMBER_LOGIN_REQUEST);

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

    @Test
    void 댓글은_500자_이하여야한다() {
        String cookie = login(MEMBER_LOGIN_REQUEST);

        RestAssured.given().log().all()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .body(new CommentRequest("베리".repeat(251)))
                .when().post("/rooms/" + room1.getId() + "/comments")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 댓글은_1자_이상이여야한다() {
        String cookie = login(MEMBER_LOGIN_REQUEST);

        RestAssured.given().log().all()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .body(new CommentRequest("  "))
                .when().post("/rooms/" + room1.getId() + "/comments")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 유저는_자신의_댓글을_삭제할_수_있다() {
        String cookie = login(MEMBER_LOGIN_REQUEST);

        CommentResponse commentResponse = commentRequest(cookie, "댓글");

        RestAssured.given().log().all()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .when().delete("/rooms/" + room1.getId() + "/comments/" + commentResponse.id())
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 유저는_다른_유저의_댓글을_삭제할_수_없다() {
        String powerCookie = login(MEMBER_LOGIN_REQUEST);
        String blackcatCookie = login(MEMBER2_LOGIN_REQUEST);

        CommentResponse commentResponse = commentRequest(blackcatCookie, "댓글");

        RestAssured.given().log().all()
                .cookie(powerCookie)
                .contentType(ContentType.JSON)
                .when().delete("/rooms/" + room1.getId() + "/comments/" + commentResponse.id())
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 유저는_존재하지_않은_댓글을_삭제할_수_없다() {
        String powerCookie = login(MEMBER_LOGIN_REQUEST);

        RestAssured.given().log().all()
                .cookie(powerCookie)
                .contentType(ContentType.JSON)
                .when().delete("/rooms/" + room1.getId() + "/comments/" + Long.MAX_VALUE)
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


    private static String login(final MemberLoginRequest loginRequest) {
        Response response = RestAssured.given().log().all()
                .body(loginRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/login");

        return response.header("Set-Cookie");
    }

    private CommentResponse commentRequest(final String cookie, final String comment) {
        return RestAssured.given().log().all()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .body(new CommentRequest(comment))
                .when().post("/rooms/" + room1.getId() + "/comments")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(CommentResponse.class);
    }
}
