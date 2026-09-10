package com.myerasmusjourney.backend.e2e;

import com.myerasmusjourney.backend.dto.CommentDTO;
import io.restassured.response.Response;
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

    private CommentDTO postComment() throws JSONException {

        when()
                .get("/api/v1/experiences/1")
                .then()
                .statusCode(200);

        JSONObject body = new JSONObject();
        body.put("description", "test description");

        Response response = given()
                .cookie("AuthToken", this.token)
                .contentType("application/json")
                .body(body.toString()).
                when()
                .post("/api/v1/experiences/1/comments");

        return response.getBody().as(CommentDTO.class);
    }

    @Test
    void testPostComment() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("description", "test description");

        obtainToken("test@email.com");

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

        obtainToken("test@email.com");

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
                .body("", hasSize(greaterThan(0)))
                .body("[0].experienceId", equalTo(2));
    }

    @Test
    void testDeleteCommentByIdSuccess(){
        CommentDTO commentDTO;

        try {
            obtainToken("exampleuser1@email.com");
            commentDTO = postComment();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        given()
                .cookie("AuthToken", this.token)
                .when()
                .delete("/api/v1/comments/"+commentDTO.id())
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(commentDTO.id().intValue()))
                .body("description", equalTo(commentDTO.description()))
                .body("date", equalTo(commentDTO.date().toString()));
    }

    @Test
    void testDeleteCommentByAdmin(){
        CommentDTO commentDTO;

        try {
            obtainToken("exampleuser1@email.com");
            commentDTO = postComment();
            obtainToken("testadmin@email.com");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        given()
                .cookie("AuthToken", this.token)
                .when()
                .delete("/api/v1/comments/"+commentDTO.id())
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(commentDTO.id().intValue()))
                .body("description", equalTo(commentDTO.description()))
                .body("date", equalTo(commentDTO.date().toString()));
    }

    @Test
    void testDeleteCommentByIdFails(){
        try {
            obtainToken("test@email.com");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        given()
                .cookie("AuthToken", this.token)
                .when()
                .delete("/api/v1/comments/1")
                .then()
                .statusCode(403);
    }

    @Test
    void testDeleteCommentWithoutAuthentication(){

        given()
                .when()
                .delete("/api/v1/comments/1")
                .then()
                .statusCode(401);
    }
}
