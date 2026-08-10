package com.myerasmusjourney.backend.e2e;

import com.myerasmusjourney.backend.TestDataBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Tag("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersTest extends TestDataBase {

    private static final Logger log = LoggerFactory.getLogger(UsersTest.class);
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void deleteCredentials(){
        this.token = null;
    }

    private String token = null;

    private void obtainToken(boolean admin) throws JSONException {
        if(token != null) return;

        JSONObject body = new JSONObject();
        if (!admin) body.put("username","test@email.com");
        else body.put("username","testadmin@email.com");
        body.put("password", "password");

        Response response =
                given()
                        .contentType("application/json")
                        .body(body.toString()).
                        when()
                        .post("/api/v1/auth/login");

        this.token = response.getCookie("AuthToken");
    }

    @Test
    void testSuccessfulCreateUser() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("email","user@gmail.com");
        body.put("displayName", "test");
        body.put("fullName", "testUser");
        body.put("city", "Munich");
        body.put("country", "Germany");
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

    @Test
    void testGetUserInfo(){
        try {
            obtainToken(false);
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException(e);
        }

        given()
                .cookie("AuthToken", this.token)
            .when()
                .get("/api/v1/users/me")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", notNullValue())
                .body("id", greaterThan(0))
                .body("displayName", notNullValue())
                .body("email", notNullValue());
    }

    @Test
    void testGetUserByIdSuccess(){
        try {
            obtainToken(false);
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException(e);
        }

        given()
                .cookie("AuthToken", this.token)
                .when()
                .get("/api/v1/users/2")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", notNullValue())
                .body("id", equalTo(2))
                .body("fullName", notNullValue())
                .body("displayName", notNullValue())
                .body("email", notNullValue());
    }

    @Test
    void testGetUserByAdmin(){
        try {
            obtainToken(true);
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException(e);
        }

        given()
                .cookie("AuthToken", this.token)
                .when()
                .get("/api/v1/users/1")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", notNullValue())
                .body("id", equalTo(1))
                .body("fullName", notNullValue())
                .body("displayName", notNullValue())
                .body("email", notNullValue())
                .body("studyLocation", notNullValue());
    }

    @Test
    void testGetUserByIdSuccessNoStudyLocation(){
        try {
            obtainToken(false);
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException(e);
        }

        given()
                .cookie("AuthToken", this.token)
                .when()
                .get("/api/v1/users/2")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", notNullValue())
                .body("id", equalTo(2))
                .body("fullName", notNullValue())
                .body("displayName", notNullValue())
                .body("email", notNullValue())
                .body("studyLocation", nullValue());
    }

    @Test
    void testGetUserByIdNotFound(){
        try {
            obtainToken(true);
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException(e);
        }

        given()
                .cookie("AuthToken", this.token)
                .when()
                .get("/api/v1/users/0")
                .then()
                .statusCode(404);
    }

    @Test
    void testGetUserByIdFail(){
        try {
            obtainToken(false);
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException(e);
        }

        given()
                .cookie("AuthToken", this.token)
                .when()
                .get("/api/v1/users/1")
                .then()
                .statusCode(403);
    }
}
