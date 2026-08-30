package com.myerasmusjourney.backend.e2e;

import com.myerasmusjourney.backend.TestDataBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticatedE2ETest extends TestDataBase {

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

    protected String token = null;

    protected void obtainToken(String email) throws JSONException {
        if(token != null) return;

        JSONObject body = new JSONObject();
        body.put("username", email);
        body.put("password", "password");

        Response response =
                given()
                        .contentType("application/json")
                        .body(body.toString()).
                        when()
                        .post("/api/v1/auth/login");

        this.token = response.getCookie("AuthToken");
    }
}
