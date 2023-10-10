package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.service.dto.MemberLoginRequest;
import com.digginroom.digginroom.repository.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static com.digginroom.digginroom.TestFixture.MEMBER_LOGIN_REQUEST;
import static com.digginroom.digginroom.TestFixture.파워;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("NonAsciiCharacters")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemberLoginControllerTest extends ControllerTest {

    @Autowired
    private MemberRepository memberRepository;


    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        memberRepository.save(파워());
    }

    @Test
    void 회원가입한_사용자는_로그인을_할_수_있다() {
        RestAssured.given().log().all()
                .body(MEMBER_LOGIN_REQUEST)
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 회원가입을_하지않은_사용자는_로그인을_할_수_없다() {
        RestAssured.given().log().all()
                .body(new MemberLoginRequest("kong123", "kong123!"))
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then().log().all()
                .body("timeStamp", Matchers.notNullValue())
                .body("errorMessage", equalTo("회원 정보가 없습니다."));
    }

    @Test
    void 비밀번호가_틀린_사용자는_로그인을_할_수_없다() {
        RestAssured.given().log().all()
                .body(new MemberLoginRequest(파워().getUsername(), "kong123!"))
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                .body("timeStamp", Matchers.notNullValue())
                .body("errorMessage", equalTo("회원 정보가 없습니다."));
    }


    @Test
    void 회원가입한_사용자는_로그인을_연속으로_할_수_있다() {
        RestAssured.given().log().all()
                .body(MEMBER_LOGIN_REQUEST)
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.OK.value());

        RestAssured.given().log().all()
                .body(MEMBER_LOGIN_REQUEST)
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 게스트로_로그인을_할_수_있다() {
        RestAssured.given().log().all()
                .when()
                .post("/login/guest")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("hasFavorite", is(false))
                .body("memberId", isA(Integer.class));
    }
}
