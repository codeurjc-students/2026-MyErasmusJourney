package com.myerasmusjourney.backend.e2e;

import com.myerasmusjourney.backend.TestDataBase;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

@Tag("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthTest extends TestDataBase {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testSuccessfulLogIn() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("username","test@email.com");
        body.put("password", "password");

        given()
                .contentType("application/json")
                .body(body.toString()).
                when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"));
    }

    @Test
    void testFailedLogIn() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("username","test@email.com");
        body.put("password", "pasword");

        given()
                .contentType("application/json")
                .body(body.toString()).
                when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(401)
                .body("status", equalTo(401));
    }
}
