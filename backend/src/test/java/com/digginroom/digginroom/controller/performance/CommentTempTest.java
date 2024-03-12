package com.digginroom.digginroom.controller.performance;

import com.digginroom.digginroom.controller.mock.MockLoginServer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

@SuppressWarnings("NonAsciiCharacters")
public class CommentTempTest extends PerformanceController {

    private MockLoginServer mockLoginServer = new MockLoginServer(WebClient.builder());

    @Test
    void 그냥_조인() {
        String cookie = login(port);
        long time = test(() -> RestAssured.given()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .when().get("/rooms/" + ThreadLocalRandom.current().nextInt(216, 375) + "/comments/1"));
        System.out.println("그냥 조인 시간: " + time);
    }

    @Test
    void 커버링_인덱스_적용() {
        String cookie = login(port);
        long time = test(() -> RestAssured.given()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .when().get("/rooms/" + ThreadLocalRandom.current().nextInt(216, 375) + "/comments/2")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("comments", Matchers.hasSize(10)));
        System.out.println("커버링 인덱스 시간: " + time);
    }

    @Test
    void 커버링_인덱스_적용2() {
        String cookie = login(port);
        long time = test(() -> RestAssured.given()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .when().get("/rooms/" + ThreadLocalRandom.current().nextInt(216, 375) + "/comments/3")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("comments", Matchers.hasSize(10)));
        System.out.println("커버링 인덱스 시간: " + time);
    }

    @Test
    void 커버링_인덱스_적용3() {
        String cookie = login(port);
        long time = test(() -> RestAssured.given()
                .cookie(cookie)
                .contentType(ContentType.JSON)
                .when().get("/rooms/" + ThreadLocalRandom.current().nextInt(216, 375) + "/comments/4")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("comments", Matchers.hasSize(10)));
        System.out.println("커버링 인덱스 시간: " + time);
    }

    private long test(Runnable runnable) {
        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        AtomicInteger num = new AtomicInteger(0);
        for (int i = 0; i < 10000; i++) {
            executorService.submit(() -> {
                runnable.run();
                num.addAndGet(1);
            });
        }
        while (num.get() < 10000) {
            try {
                System.out.println(num.get() + " - " + Thread.activeCount());
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println("예외");
            }
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    private String login(final int port) {
        return mockLoginServer.getCachedLoginValue(port);
    }
}
