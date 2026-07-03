package com.myerasmusjourney.backend.e2e;

import com.myerasmusjourney.backend.TestDataBase;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExperiencesTest extends TestDataBase {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testGetExperiences() {

        when()
                .get("/api/experiences/")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("", hasSize(greaterThan(0)))
                .body("[0].id", notNullValue())
                .body("[0].date", notNullValue())
                .body("[0].rating", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].description", notNullValue());
    }
}