package com.myerasmusjourney.backend.e2e;


import com.myerasmusjourney.backend.enumeration.Category;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@Tag("e2e")
class ExperiencesTest extends AuthenticatedE2ETest {

    @Test
    void testGetExperiences() {

        when()
                .get("/api/v1/experiences/?page=0&size=3")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("content", hasSize(greaterThan(0)))
                .body("content", hasSize(lessThanOrEqualTo(3)))
                .body("content[0].id", notNullValue())
                .body("content[0].date", notNullValue())
                .body("content[0].rating", notNullValue())
                .body("content[0].title", notNullValue())
                .body("content[0].categories", notNullValue())
                .body("content[0].description", notNullValue());
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
        JSONArray categories = new JSONArray();
        categories.put("Transportation");
        categories.put("Studies");

        JSONObject body = new JSONObject();
        body.put("title", "Experience");
        body.put("cityId", 1L);
        body.put("description", "Whatever user wants");
        body.put("rating", 5.2F);
        body.put("categories", categories);
        body.put("date", LocalDate.of(2022, 1, 13));

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
                .body("city.id", equalTo(1))
                .body("date", equalTo(LocalDate.of(2022, 1, 13).toString()))
                .body("categories", hasSize(2));
    }

    @Test
    void testPostExperienceWithoutAuthentication() throws JSONException {
        JSONArray categories = new JSONArray();
        categories.put("Transportation");
        categories.put("Studies");

        JSONObject body = new JSONObject();
        body.put("title", "Experience");
        body.put("cityId", 1L);
        body.put("description", "Whatever user wants");
        body.put("rating", 5.2F);
        body.put("categories", categories);
        body.put("date", LocalDate.of(2022, 1, 13).toString());


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
        JSONArray categories = new JSONArray();
        categories.put("Transportation");
        categories.put("Studies");

        JSONObject body = new JSONObject();
        body.put("title", "Experience");
        body.put("cityId", 0L);
        body.put("description", "Whatever user wants");
        body.put("rating", 5.2F);
        body.put("categories", categories);
        body.put("date", JSONObject.NULL);

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

    @Test
    void testGetExperienceById() {

        when()
                .get("/api/v1/experiences/1")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(1))
                .body("date", notNullValue())
                .body("rating", notNullValue())
                .body("title", notNullValue())
                .body("categories", notNullValue())
                .body("categories", hasSize(greaterThan(0)))
                .body("categories", hasSize(lessThanOrEqualTo(3)))
                .body("description", notNullValue())
                .body("author", notNullValue())
                .body("city", notNullValue());
    }

    @Test
    void testGetExperienceByIdNotFound() {

        when()
                .get("/api/v1/experiences/0")
                .then()
                .statusCode(404);
    }
}