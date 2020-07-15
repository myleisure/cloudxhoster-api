package com.cloudxhoster.api.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

@QuarkusTest
class UserResourceTest {

    @Test
    @DisplayName("Get all users")
    public void getAll() {
        given()
                .when().get("/api/v1/users")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Register a user")
    @Disabled
    public void register() {
        given()
                .contentType(ContentType.JSON)
                .queryParams(Map.of("firstname", "john",
                        "lastname", "Wick",
                        "email", "john.wick@gmail.com",
                        "country_id", "73"))
                .when().post("/api/users/register")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);
    }
}