package com.digginroom.digginroom.controller;

import static com.digginroom.digginroom.TestFixture.MEMBER_LOGIN_REQUEST;
import static com.digginroom.digginroom.domain.room.Genre.DANCE;
import static com.digginroom.digginroom.domain.room.Genre.RNB;
import static com.digginroom.digginroom.domain.room.Genre.ROCK;

import com.digginroom.digginroom.TestFixture;
import com.digginroom.digginroom.membergenre.service.dto.MemberGenresResponse;
import com.digginroom.digginroom.service.dto.FavoriteGenresRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
class MemberGenreControllerTest extends ControllerTest {

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
    void 취향_장르들을_선택한다() {
        RestAssured.given().log().all()
                .cookie(cookie)
                .body(new FavoriteGenresRequest(List.of(DANCE, ROCK)))
                .contentType(ContentType.JSON)
                .when()
                .post("/member/favorite-genres")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 없는_취향_장르를_선택하면_예외가_발생한다() {
        RestAssured.given().log().all()
                .cookie(cookie)
                .body("{\n"
                        + "  \"favoriteGenres\": [\n"
                        + "    \"없는장르\",\n"
                        + "  ]\n"
                        + "}")
                .contentType(ContentType.JSON)
                .when()
                .post("/member/favorite-genres")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 중복된_취향_장르가_있으면_예외가_발생한다() {
        RestAssured.given().log().all()
                .cookie(cookie)
                .body(new FavoriteGenresRequest(List.of(ROCK, ROCK)))
                .contentType(ContentType.JSON)
                .when()
                .post("/member/favorite-genres")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 취향_정보를_한_번_이상_전달하면_예외가_발생한다() {
        RestAssured.given().log().all()
                .cookie(cookie)
                .body(new FavoriteGenresRequest(List.of(ROCK)))
                .contentType(ContentType.JSON)
                .when()
                .post("/member/favorite-genres")
                .then()
                .statusCode(HttpStatus.OK.value());

        RestAssured.given().log().all()
                .cookie(cookie)
                .body(new FavoriteGenresRequest(List.of(RNB)))
                .contentType(ContentType.JSON)
                .when()
                .post("/member/favorite-genres")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 유저가_이미_장르가_선택했는지_여부를_반환한다() {
        RestAssured.given().log().all()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .when()
                .get("/member/genres")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("hasFavorite", Matchers.notNullValue())
                .extract().as(MemberGenresResponse.class);
    }
}
