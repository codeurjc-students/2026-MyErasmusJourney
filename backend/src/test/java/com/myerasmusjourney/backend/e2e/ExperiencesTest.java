package com.myerasmusjourney.backend.e2e;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.enumeration.Category;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@Tag("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExperiencesTest extends TestDataBase {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

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
}