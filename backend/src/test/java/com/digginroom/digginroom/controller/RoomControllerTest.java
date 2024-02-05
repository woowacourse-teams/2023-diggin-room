package com.digginroom.digginroom.controller;

import static com.digginroom.digginroom.TestFixture.나무;
import static com.digginroom.digginroom.TestFixture.차이;
import static com.digginroom.digginroom.TestFixture.파워;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.hasSize;

import com.digginroom.digginroom.controller.mock.MockLoginServer;
import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.repository.RoomRepository;
import com.digginroom.digginroom.service.dto.RoomRequest;
import com.digginroom.digginroom.service.dto.RoomResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("auth")
@SuppressWarnings("NonAsciiCharacters")
class RoomControllerTest extends ControllerTest {

    private MemberRepository memberRepository;
    private RoomRepository roomRepository;
    private MockLoginServer mockLoginServer;
    private Room room1;
    private Room room2;

    @Autowired
    public RoomControllerTest(MemberRepository memberRepository,
                              RoomRepository roomRepository,
                              ObjectProvider<MockLoginServer> mockLoginServerObjectProvider
    ) {
        this.memberRepository = memberRepository;
        this.roomRepository = roomRepository;
        this.mockLoginServer = mockLoginServerObjectProvider.getObject();
        System.out.println(123123);
    }

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        memberRepository.save(파워());
        room1 = roomRepository.save(나무());
        room2 = roomRepository.save(차이());
    }

    @Test
    void 로그인된_사용자는_룸을_탐색할_수_있다() {
        String cookie = login(port);

        RestAssured.given()
                .cookie(cookie)
                .when()
                .get("/room")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("track.title", Matchers.notNullValue())
                .body("track.artist", Matchers.notNullValue())
                .body("track.superGenre", Matchers.notNullValue())
                .extract().as(RoomResponse.class);
    }

    private String login(final int port) {
        return mockLoginServer.getCachedLoginValue(port);
    }

    @Test
    void 로그인하지_않은_사용자는_룸을_탐색할_수_없다() {
        RestAssured.given()
                .when()
                .get("/room")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 룸을_스크랩할_수_있다() {
        String cookie = login(port);

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/room/scrap")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 룸_스크랩을_취소할_수_있다() {
        String cookie = login(port);

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/room/scrap")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .delete("/room/scrap")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 싫어요한_룸을_스크랩_할_수_있다() {
        String cookie = login(port);

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/room/dislike")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/room/scrap")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 룸을_싫어요_할_수_있다() {
        String cookie = login(port);

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/room/dislike")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 싫어요한_룸도_여러번_싫어요할_수_있다() {
        String cookie = login(port);

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/room/dislike")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/room/dislike")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 스크랩한_룸을_싫어요_할_수_있다() {
        String cookie = login(port);

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/room/scrap")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/room/dislike")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 싫어요한_룸을_취소할_수_있디() {
        String cookie = login(port);

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/room/dislike")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .delete("/room/dislike")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 싫어요하지_않은_룸은_싫어요_취소할_수_없다() {
        String cookie = login(port);

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .delete("/room/dislike")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 스크랩을_하지_않은_유저는_빈_스크랩_목록을_조회할_수_있다() {
        String cookie = login(port);

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .get("/room/scrap")
                .then().log().all()
                .body("rooms", emptyCollectionOf(List.class));
    }

    @Test
    void 스크랩을_한_유저는_스크랩_목록을_조회할_수_있다() {
        String cookie = login(port);

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/room/scrap")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room2.getId()))
                .post("/room/scrap")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .get("/room/scrap")
                .then().log().all()
                .body("rooms", hasSize(2));
    }

    @Test
    void 유저가_룸을_스크랩하면_해당_룸의_스크랩_수가_올라간다() {
        String cookie = login(port);

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/room/scrap")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        Long scrapCount = roomRepository.findById(room1.getId())
                .get()
                .getScrapCount();
        assertThat(scrapCount).isEqualTo(1L);
    }

    @Test
    void 유저가_룸을_언스크랩하면_해당_룸의_스크랩_수가_감소한다() {
        String cookie = login(port);

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .post("/room/scrap")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given()
                .cookie(cookie)
                .when()
                .contentType(ContentType.JSON)
                .body(new RoomRequest(room1.getId()))
                .delete("/room/scrap")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        Long scrapCount = roomRepository.findById(room1.getId())
                .get()
                .getScrapCount();
        assertThat(scrapCount).isEqualTo(0L);
    }
}
