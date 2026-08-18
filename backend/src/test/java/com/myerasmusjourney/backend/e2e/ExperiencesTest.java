package com.myerasmusjourney.backend.e2e;


import com.myerasmusjourney.backend.enumeration.Category;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@Tag("e2e")
class ExperiencesTest extends AuthenticatedE2ETest {

    @Test
    void testGetExperiences() {

        when()
                .get("/api/v1/experiences/")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("", hasSize(greaterThan(0)))
                .body("[0].id", notNullValue())
                .body("[0].date", notNullValue())
                .body("[0].rating", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].category", notNullValue())
                .body("[0].description", notNullValue());
    }

    @Test
    void testGetCategories() {

        when()
                .get("/api/v1/experiences/categories")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("", hasSize(greaterThan(0)))
                .body("[0]", equalTo(Category.Accommodation.toString()))
                .body("[1]", equalTo(Category.Culture.toString()))
                .body("[2]", equalTo(Category.Documentation.toString()))
                .body("[3]", equalTo(Category.Gastronomy.toString()))
                .body("[4]", equalTo(Category.Personal_Experience.toString()));
    }

    @Test
    void testPostExperience() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("title", "Experience");
        body.put("cityId", 1L);
        body.put("description", "Whatever user wants");
        body.put("rating", 5.2F);
        body.put("category", "Transportation");

        obtainToken(false);

        given()
                .cookie("AuthToken", this.token)
                .contentType("application/json")
                .body(body.toString()).
                when()
                .post("/api/v1/experiences/")
                .then()
                .statusCode(201)
                .body("id", greaterThan(0))
                .body("title", equalTo(body.get("title")))
                .body("description", equalTo(body.get("description")))
                .body("rating", equalTo(5.2F))
                .body("city.id", equalTo(1));
    }

    @Test
    void testPostExperienceWithoutAuthentication() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("title", "Experience");
        body.put("cityId", 1L);
        body.put("description", "Whatever user wants");
        body.put("rating", 5.2F);
        body.put("category", "Transportation");


        given()
                .cookie("AuthToken", this.token)
                .contentType("application/json")
                .body(body.toString()).
                when()
                .post("/api/v1/experiences/")
                .then()
                .statusCode(401);
    }

    @Test
    void testPostExperienceWithoutValidCity() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("title", "Experience");
        body.put("cityId", 0L);
        body.put("description", "Whatever user wants");
        body.put("rating", 5.2F);
        body.put("category", "Transportation");

        obtainToken(false);

        given()
                .cookie("AuthToken", this.token)
                .contentType("application/json")
                .body(body.toString()).
                when()
                .post("/api/v1/experiences/")
                .then()
                .statusCode(404);
    }
}