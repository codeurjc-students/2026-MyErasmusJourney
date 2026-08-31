package com.myerasmusjourney.backend.e2e;

import com.myerasmusjourney.backend.dto.UserDTO;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

@Tag("e2e")
public class UsersTest extends AuthenticatedE2ETest {

    private static final Logger log = LoggerFactory.getLogger(UsersTest.class);

    private static Long deletedUserId;

    private void createUserToDelete() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("email","deletedUser@gmail.com");
        body.put("displayName", "test");
        body.put("fullName", "testUser");
        body.put("city", "Munich");
        body.put("country", "Germany");
        body.put("password", "password");
        body.put("passwordConfirmation", "password");

        Response response = given()
                .contentType("application/json")
                .body(body.toString()).
                when()
                .post("/api/v1/users/");

        ResponseBody responseBody = response.body();
        if(response.statusCode() == 201){
            UserDTO userDTO = responseBody.as(UserDTO.class);
            deletedUserId = userDTO.id();
        }
    }

    private void authenticateAsDeletedUser() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("username","deletedUser@gmail.com");
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
            obtainToken("test@email.com");
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
            obtainToken("test@email.com");
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
                .body("roles", equalTo(List.of("USER")));
    }

    @Test
    void testGetUserByAdmin(){
        try {
            obtainToken("testadmin@email.com");
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
                .body("studyLocation", notNullValue())
                .body("roles", equalTo(List.of("USER", "ADMIN")));
    }

    @Test
    void testGetUserByIdSuccessNoStudyLocation(){
        try {
            obtainToken("test@email.com");
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
                .body("studyLocation", nullValue())
                .body("roles", equalTo(List.of("USER")));
    }

    @Test
    void testGetUserByIdNotFound(){
        try {
            obtainToken("testadmin@email.com");
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
            obtainToken("test@email.com");
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

    @Test
    void testDeleteUserByIdSuccess(){
        try{
            createUserToDelete();
            authenticateAsDeletedUser();
            given()
                    .cookie("AuthToken", this.token)
                    .when()
                    .delete("/api/v1/users/"+deletedUserId)
                    .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("id", notNullValue())
                    .body("id", equalTo(deletedUserId.intValue()))
                    .body("fullName", equalTo("testUser"))
                    .body("displayName", equalTo("test"))
                    .body("email", equalTo("deletedUser@gmail.com"))
                    .body("roles", equalTo(List.of("USER")));
        } catch (JSONException e){
            fail();
        }
    }

    @Test
    void testDeleteUserByIdNotFound(){
        try{
            obtainToken("testadmin@email.com");
            given()
                    .cookie("AuthToken", this.token)
                    .when()
                    .delete("/api/v1/users/0")
                    .then()
                    .statusCode(404);
        } catch (JSONException e){
            fail();
        }
    }

    @Test
    void testDeleteUserByIdFail(){
        try{
            createUserToDelete();
            obtainToken("test@email.com");
            given()
                    .cookie("AuthToken", this.token)
                    .when()
                    .delete("/api/v1/users/"+deletedUserId)
                    .then()
                    .statusCode(403);
        } catch (JSONException e){
            fail();
        }
    }

    @Test
    void testDeleteUserByAdmin(){
        try{
            createUserToDelete();
            obtainToken("testadmin@email.com");
            given()
                    .cookie("AuthToken", this.token)
                    .when()
                    .delete("/api/v1/users/"+deletedUserId)
                    .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("id", notNullValue())
                    .body("id", equalTo(deletedUserId.intValue()))
                    .body("fullName", equalTo("testUser"))
                    .body("displayName", equalTo("test"))
                    .body("email", equalTo("deletedUser@gmail.com"))
                    .body("roles", equalTo(List.of("USER")));
        } catch (JSONException e){
            fail();
        }
    }

    @Test
    void testDeleteUserByIdWithoutAuthentication(){
        try{
            createUserToDelete();
            given()
                    .when()
                    .delete("/api/v1/users/"+deletedUserId)
                    .then()
                    .statusCode(401);
        } catch (JSONException e){
            fail();
        }
    }

    @Test
    void testGetUserExperiencesByIdSuccess(){
        try {
            obtainToken("exampleuser1@email.com");
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException(e);
        }

        given()
                .cookie("AuthToken", this.token)
                .when()
                .get("/api/v1/users/3/experiences")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("", hasSize(greaterThan(0)))
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].description", notNullValue())
                .body("[0].date", notNullValue())
                .body("[0].categories", hasSize(greaterThan(0)))
                .body("[0].categories", hasSize(lessThan(4)));
    }

    @Test
    void testGetUserEmptyExperiencesByIdSuccess(){
        try {
            obtainToken("test@email.com");
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException(e);
        }

        given()
                .cookie("AuthToken", this.token)
                .when()
                .get("/api/v1/users/2/experiences")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("", hasSize(0));
    }

    @Test
    void testGetUserExperiencesByAdmin(){
        try {
            obtainToken("testadmin@email.com");
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException(e);
        }

        given()
                .cookie("AuthToken", this.token)
                .when()
                .get("/api/v1/users/3/experiences")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("", hasSize(greaterThan(0)))
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].description", notNullValue())
                .body("[0].date", notNullValue())
                .body("[0].categories", hasSize(greaterThan(0)))
                .body("[0].categories", hasSize(lessThan(4)));
    }

    @Test
    void testGetUserExperiencesByIdFails(){
        try {
            obtainToken("test@email.com");
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException(e);
        }

        given()
                .cookie("AuthToken", this.token)
                .when()
                .get("/api/v1/users/3/experiences")
                .then()
                .statusCode(403);
    }

    @Test
    void testGetUserExperiencesWithoutAuthentication(){

        given()
                .when()
                .get("/api/v1/users/3/experiences")
                .then()
                .statusCode(401);
    }

    @Test
    void testGetUserCommentsByIdSuccess(){
        try {
            obtainToken("exampleuser1@email.com");
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException(e);
        }

        given()
                .cookie("AuthToken", this.token)
                .when()
                .get("/api/v1/users/3/comments")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("", hasSize(greaterThan(0)))
                .body("[0].id", notNullValue())
                .body("[0].description", notNullValue())
                .body("[0].date", notNullValue())
                .body("[0].experienceId", greaterThan(0))
                .body("[0].authorName", notNullValue());
    }

    @Test
    void testGetUserCommentsByAdmin(){
        try {
            obtainToken("testadmin@email.com");
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException(e);
        }

        given()
                .cookie("AuthToken", this.token)
                .when()
                .get("/api/v1/users/3/comments")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("", hasSize(greaterThan(0)))
                .body("[0].id", notNullValue())
                .body("[0].description", notNullValue())
                .body("[0].date", notNullValue())
                .body("[0].experienceId", greaterThan(0))
                .body("[0].authorName", notNullValue());
    }

    @Test
    void testGetUserCommentsByIdFails(){
        try {
            obtainToken("test@email.com");
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException(e);
        }

        given()
                .cookie("AuthToken", this.token)
                .when()
                .get("/api/v1/users/3/comments")
                .then()
                .statusCode(403);
    }

    @Test
    void testGetUserCommentsWithoutAuthentication(){

        given()
                .when()
                .get("/api/v1/users/3/comments")
                .then()
                .statusCode(401);
    }
}
