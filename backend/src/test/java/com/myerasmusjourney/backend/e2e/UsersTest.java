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
import static org.hamcrest.Matchers.*;

@Tag("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersTest extends TestDataBase {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testSuccessfulCreateUser() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("email","user@gmail.com");
        body.put("displayName", "test");
        body.put("fullName", "testUser");
        body.put("password", "password");
        body.put("passwordConfirmation", "password");

        given()
                .contentType("application/json")
                .body(body.toString()).
        when()
            .post("/api/v1/users/")
        .then()
            .statusCode(201)
            .body("id", greaterThan(0))
            .body("fullName", equalTo(body.get("fullName")))
            .body("email", equalTo(body.get("email")))
            .body("displayName", equalTo(body.get("displayName")));
    }

    @Test
    void testEmailAlreadyRegistered() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("email","test@gmail.com");
        body.put("displayName", "test");
        body.put("fullName", "testUser");
        body.put("password", "password");
        body.put("passwordConfirmation", "password");

        given()
                .contentType("application/json")
                .body(body.toString()).
                when()
                .post("/api/v1/users/")
                .then()
                .statusCode(201);

        given()
                .contentType("application/json")
                .body(body.toString()).
                when()
                .post("/api/v1/users/")
                .then()
                .statusCode(400);
    }

    @Test
    void testPasswordMismatch() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("email","user@gmail.com");
        body.put("displayName", "test");
        body.put("fullName", "testUser");
        body.put("password", "Password");
        body.put("passwordConfirmation", "password");

        given()
                .contentType("application/json")
                .body(body.toString()).
                when()
                .post("/api/v1/users/")
                .then()
                .statusCode(400);
    }
}
