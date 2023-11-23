package com.digginroom.digginroom;


import com.digginroom.digginroom.util.DatabaseCleanerExtension;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@ExtendWith(DatabaseCleanerExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class ControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setPortUp() {
        RestAssured.port = port;
    }
}
