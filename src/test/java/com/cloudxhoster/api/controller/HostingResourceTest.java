package com.cloudxhoster.api.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class HostingResourceTest {

    @Test
    @Disabled
    @DisplayName("On ne peut pas tester avec un mediatype multipart form data avec quarkus")
    void host() {
        final var formParams = Map.of("domainName", "yolescopains.com",
                "websiteName", "yolescopains",
                "region", "myregion",
                "userId", "63",
                "archiveUrl", "http://apache.crihan.fr/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.zip");
        given().contentType(ContentType.URLENC).formParams(formParams)
                .when()
                .put("/api/host")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}