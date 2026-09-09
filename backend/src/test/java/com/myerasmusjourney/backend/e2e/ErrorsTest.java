package com.myerasmusjourney.backend.e2e;

import com.myerasmusjourney.backend.TestDataBase;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.when;

@Tag("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ErrorsTest extends TestDataBase {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testInternalError(){
        when()
                .get("/api/v1/tests/500")
                .then()
                .statusCode(500);
    }

}
