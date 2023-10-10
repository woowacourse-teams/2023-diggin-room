package com.digginroom.digginroom.controller;

import static com.digginroom.digginroom.TestFixture.MEMBER_LOGIN_REQUEST;
import static com.digginroom.digginroom.domain.Genre.DANCE;
import static com.digginroom.digginroom.domain.Genre.RNB;
import static com.digginroom.digginroom.domain.Genre.ROCK;

import com.digginroom.digginroom.TestFixture;
import com.digginroom.digginroom.service.dto.FavoriteGenresRequest;
import com.digginroom.digginroom.service.dto.MemberDetailsResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SuppressWarnings("NonAsciiCharacters")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemberOperationControllerTest extends ControllerTest {

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
                .body(new FavoriteGenresRequest(List.of(DANCE.getName(), ROCK.getName())))
                .contentType(ContentType.JSON)
                .when()
                .post("/member/favorite-genres")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 없는_취향_장르를_선택하면_예외가_발생한다() {
        RestAssured.given().log().all()
                .cookie(cookie)
                .body(new FavoriteGenresRequest(List.of("없는 장르")))
                .contentType(ContentType.JSON)
                .when()
                .post("/member/favorite-genres")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 중복된_취향_장르가_있으면_예외가_발생한다() {
        RestAssured.given().log().all()
                .cookie(cookie)
                .body(new FavoriteGenresRequest(List.of(ROCK.getName(), ROCK.getName())))
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
                .body(new FavoriteGenresRequest(List.of(ROCK.getName())))
                .contentType(ContentType.JSON)
                .when()
                .post("/member/favorite-genres")
                .then()
                .statusCode(HttpStatus.OK.value());

        RestAssured.given().log().all()
                .cookie(cookie)
                .body(new FavoriteGenresRequest(List.of(RNB.getName())))
                .contentType(ContentType.JSON)
                .when()
                .post("/member/favorite-genres")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 아이디_유저네임_취향정보수집여부를_포함한_회원정보를_응답한다() {
        RestAssured.given().log().all()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .when()
                .get("/member/me")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("memberId", Matchers.notNullValue())
                .body("username", Matchers.notNullValue())
                .body("hasFavorite", Matchers.notNullValue())
                .extract().as(MemberDetailsResponse.class);
    }
}
