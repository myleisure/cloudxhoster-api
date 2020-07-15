package com.cloudxhoster.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ExampleResourceTest {

    @Test
    @Disabled
    public void testHelloEndpoint() {
        given()
          .when().get("/api/countries")
          .then()
             .statusCode(200)
                .contentType(ContentType.JSON);
    }

}