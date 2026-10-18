package com.myerasmusjourney.backend.e2e;

import com.myerasmusjourney.backend.TestDataBase;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

@Tag("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MultimediaTest extends TestDataBase {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testGetProfileImage(){

        given()
                .when()
                .get("/api/v1/multimedia/users/1")
                .then()
                .statusCode(200);
    }

    @Test
    void testGetProfileImageNotFound(){

        given()
                .when()
                .get("/api/v1/multimedia/users/0")
                .then()
                .statusCode(404);
    }

    @Test
    void testGetExperienceMultimedia(){

        given()
                .when()
                .get("/api/v1/multimedia/experiences/1")
                .then()
                .statusCode(200);
    }

    @Test
    void testGetExperienceMultimediaNotFound(){

        given()
                .when()
                .get("/api/v1/multimedia/experiences/0")
                .then()
                .statusCode(404);
    }
}
