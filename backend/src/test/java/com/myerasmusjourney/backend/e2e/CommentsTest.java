package com.myerasmusjourney.backend.e2e;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@Tag("e2e")
public class CommentsTest extends AuthenticatedE2ETest{

    @Test
    void testPostComment() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("description", "test description");

        obtainToken(false);

        given()
                .cookie("AuthToken", this.token)
                .contentType("application/json")
                .body(body.toString()).
                when()
                .post("/api/v1/experiences/1/comments")
                .then()
                .statusCode(201)
                .body("id", greaterThan(0))
                .body("description", equalTo(body.get("description")))
                .body("date", equalTo(LocalDate.now().toString()))
                .body("experience.id", equalTo(1));
    }

    @Test
    void testGetComments() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("description", "test description");

        obtainToken(false);

        given()
                .cookie("AuthToken", this.token)
                .contentType("application/json")
                .body(body.toString()).
                when()
                .post("/api/v1/experiences/2/comments")
                .then()
                .statusCode(201);

        when()
                .get("/api/v1/experiences/2/comments")
                .then()
                .statusCode(200)
                .body("", hasSize(greaterThan(0)));
    }
}
