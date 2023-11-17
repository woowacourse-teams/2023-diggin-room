package com.digginroom.digginroom.controller;

import static com.digginroom.digginroom.TestFixture.COMMENT_UPDATE_REQUEST;
import static com.digginroom.digginroom.TestFixture.MEMBER2_LOGIN_REQUEST;
import static com.digginroom.digginroom.TestFixture.MEMBER_LOGIN_REQUEST;
import static com.digginroom.digginroom.TestFixture.나무;
import static com.digginroom.digginroom.TestFixture.블랙캣;
import static com.digginroom.digginroom.TestFixture.차이;
import static com.digginroom.digginroom.TestFixture.파워;
import static org.assertj.core.api.Assertions.assertThat;

import com.digginroom.digginroom.TestFixture;
import com.digginroom.digginroom.domain.comment.Comment;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.repository.CommentRepository;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.repository.RoomRepository;
import com.digginroom.digginroom.service.dto.CommentRequest;
import com.digginroom.digginroom.service.dto.CommentResponse;
import com.digginroom.digginroom.service.dto.CommentsResponse;
import com.digginroom.digginroom.service.dto.MemberLoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
class CommentControllerTest extends ControllerTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CommentRepository commentRepository;

    private Room 나무;
    private Room 차이;
    private Member 파워;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        파워 = memberRepository.save(파워());
        memberRepository.save(블랙캣());
        나무 = roomRepository.save(나무());
        차이 = roomRepository.save(차이());
    }

    @Test
    void 댓글을_작성_할_수_있다() {
        String cookie = login(MEMBER_LOGIN_REQUEST);

        RestAssured.given().log().all()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .body(TestFixture.COMMENT_REQUEST)
                .when().post("/rooms/" + 나무.getId() + "/comments")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .body("writer", Matchers.startsWith("user-"))
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
                .when().post("/rooms/" + 나무.getId() + "/comments")
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
                .when().post("/rooms/" + 나무.getId() + "/comments")
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
                .when().delete("/rooms/" + 나무.getId() + "/comments/" + commentResponse.id())
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
                .when().delete("/rooms/" + 나무.getId() + "/comments/" + commentResponse.id())
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 유저는_존재하지_않은_댓글을_삭제할_수_없다() {
        String powerCookie = login(MEMBER_LOGIN_REQUEST);

        RestAssured.given().log().all()
                .cookie(powerCookie)
                .contentType(ContentType.JSON)
                .when().delete("/rooms/" + 나무.getId() + "/comments/" + Long.MAX_VALUE)
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void 유저가_마지막으로_본_댓글과_볼_댓글_개수를_전달하지_않으면_최신_댓글_10개의_댓글들을_볼_수_있다() {
        for (int i = 1; i <= 15; i++) {
            commentRepository.save(new Comment(나무.getId(), "댓글" + i, 파워));
        }

        String cookie = login(MEMBER_LOGIN_REQUEST);

        List<String> comments = RestAssured.given().log().all()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .when().get("/rooms/" + 나무.getId() + "/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("comments", Matchers.hasSize(10))
                .extract().as(CommentsResponse.class)
                .comments().stream()
                .map(CommentResponse::comment)
                .collect(Collectors.toList());

        assertThat(comments).containsExactly(
                "댓글15", "댓글14", "댓글13", "댓글12", "댓글11", "댓글10", "댓글9", "댓글8", "댓글7", "댓글6"
        );
    }

    @Test
    void 유저가_마지막으로_본_댓글을_전달하면_더_이전에_작성한_룸의_댓글들을_볼_수_있다() {
        for (int i = 1; i <= 15; i++) {
            commentRepository.save(new Comment(나무.getId(), "댓글" + i, 파워));
        }

        String cookie = login(MEMBER_LOGIN_REQUEST);

        List<String> comments = RestAssured.given().log().all()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .when().get("/rooms/" + 나무.getId() + "/comments?lastCommentId=5")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("comments", Matchers.hasSize(4))
                .extract().as(CommentsResponse.class)
                .comments().stream()
                .map(CommentResponse::comment)
                .collect(Collectors.toList());

        assertThat(comments).containsExactly("댓글4", "댓글3", "댓글2", "댓글1");
    }

    @Test
    void 유저가_볼_댓글_개수를_전달하면_최대_전달한_개수만큼의_룸의_댓글들을_볼_수_있다() {
        for (int i = 1; i <= 15; i++) {
            commentRepository.save(new Comment(나무.getId(), "댓글" + i, 파워));
        }

        String cookie = login(MEMBER_LOGIN_REQUEST);

        List<String> comments = RestAssured.given().log().all()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .when().get("/rooms/" + 나무.getId() + "/comments?size=2")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("comments", Matchers.hasSize(2))
                .extract().as(CommentsResponse.class)
                .comments().stream()
                .map(CommentResponse::comment)
                .collect(Collectors.toList());

        assertThat(comments).containsExactly("댓글15", "댓글14");
    }

    @Test
    void 유저가_마지막으로_본_댓글과_볼_댓글_개수를_전달하면_더_이전에_작성한_댓글들을_최대_전달한_개수만큼_볼_수_있다() {
        for (int i = 1; i <= 15; i++) {
            commentRepository.save(new Comment(나무.getId(), "댓글" + i, 파워));
        }

        String cookie = login(MEMBER_LOGIN_REQUEST);

        List<String> comments = RestAssured.given().log().all()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .when().get("/rooms/" + 나무.getId() + "/comments?lastCommentId=7&size=2")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("comments", Matchers.hasSize(2))
                .extract().as(CommentsResponse.class)
                .comments().stream()
                .map(CommentResponse::comment)
                .collect(Collectors.toList());

        assertThat(comments).containsExactly("댓글6", "댓글5");
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
                .when().post("/rooms/" + 나무.getId() + "/comments").then().log()
                .all().statusCode(HttpStatus.CREATED.value())
                .extract().as(CommentResponse.class);
    }

    @Test
    void 유저는_자신의_댓글을_수정할_수_있다() {
        Comment comment = commentRepository.save(new Comment(나무.getId(), "댓글1", 파워));
        String cookie = login(MEMBER_LOGIN_REQUEST);

        RestAssured.given().log().all()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .body(COMMENT_UPDATE_REQUEST)
                .when().patch("/rooms/" + 나무.getId() + "/comments/" + comment.getId())
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CommentResponse.class);

        Comment updatedComment = commentRepository.getCommentById(comment.getId());
        assertThat(updatedComment.getComment()).isEqualTo(COMMENT_UPDATE_REQUEST.comment());
    }
}
