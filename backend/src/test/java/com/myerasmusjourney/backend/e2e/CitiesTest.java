package com.myerasmusjourney.backend.e2e;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

@Tag("e2e")
public class CitiesTest extends AuthenticatedE2ETest {

    @Test
    void testAddingCitySuccessfully() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("name", "Madrid");
        body.put("country", "Spain");
        body.put("description", "Capital of Spain");

        obtainToken("testadmin@email.com");

        given()
                .cookie("AuthToken", this.token)
                .contentType("application/json")
                .body(body.toString()).
                when()
                .post("/api/v1/cities/")
                .then()
                .statusCode(201)
                .body("id", greaterThan(0))
                .body("name", equalTo(body.get("name")))
                .body("country", equalTo(body.get("country")));
    }

    @Test
    void testAddingCitiesWithSameNameDifferentCountry() throws JSONException {
        JSONObject germanCity = new JSONObject();
        germanCity.put("name", "Munich");
        germanCity.put("country", "Germany");
        germanCity.put("description", "City of Germany");

        obtainToken("testadmin@email.com");

        given()
                .cookie("AuthToken", this.token)
                .contentType("application/json")
                .body(germanCity.toString()).
                when()
                .post("/api/v1/cities/")
                .then()
                .statusCode(201)
                .body("id", greaterThan(0))
                .body("name", equalTo(germanCity.get("name")))
                .body("country", equalTo(germanCity.get("country")));

        JSONObject otherCity = new JSONObject();
        otherCity.put("name", "Munich");
        otherCity.put("country", "Italy");
        otherCity.put("description", "Made up city");

        given()
                .cookie("AuthToken", this.token)
                .contentType("application/json")
                .body(otherCity.toString()).
                when()
                .post("/api/v1/cities/")
                .then()
                .statusCode(201)
                .body("id", greaterThan(0))
                .body("name", equalTo(otherCity.get("name")))
                .body("country", equalTo(otherCity.get("country")));
    }

    @Test
    void testAddingTwoTimesSameCity() throws JSONException {
        JSONObject germanCity = new JSONObject();
        germanCity.put("name", "Berlin");
        germanCity.put("country", "Germany");
        germanCity.put("description", "Capital of Germany");

        obtainToken("testadmin@email.com");

        given()
                .cookie("AuthToken", this.token)
                .contentType("application/json")
                .body(germanCity.toString()).
                when()
                .post("/api/v1/cities/")
                .then()
                .statusCode(201)
                .body("id", greaterThan(0))
                .body("name", equalTo(germanCity.get("name")))
                .body("country", equalTo(germanCity.get("country")));

        given()
                .cookie("AuthToken", this.token)
                .contentType("application/json")
                .body(germanCity.toString()).
                when()
                .post("/api/v1/cities/")
                .then()
                .statusCode(200)
                .body("id", greaterThan(0))
                .body("name", equalTo(germanCity.get("name")))
                .body("country", equalTo(germanCity.get("country")));
    }

    @Test
    void testAddingCityWithoutAdmin() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("name", "Madrid");
        body.put("country", "Spain");
        body.put("description", "Capital of Spain");

        obtainToken("test@email.com");

        given()
                .cookie("AuthToken", this.token)
                .contentType("application/json")
                .body(body.toString()).
                when()
                .post("/api/v1/cities/")
                .then()
                .statusCode(403);
    }

    @Test
    void testGetCities() {

        when()
                .get("/api/v1/cities/")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("", hasSize(greaterThan(0)))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].country", notNullValue())
                .body("[0].description", notNullValue());
    }
}
