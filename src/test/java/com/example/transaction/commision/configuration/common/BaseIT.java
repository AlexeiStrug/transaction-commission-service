package com.example.transaction.commision.configuration.common;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log
public abstract class BaseIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "";
        RestAssured.port = this.port;
        RestAssured.defaultParser = Parser.JSON;
    }

    public enum Resources {
        V1_TRANSACTIONS_COMMISSIONS("/api/v1/transactions/commissions");
        private final String endpoint;

        Resources(String endpoint) {
            this.endpoint = endpoint;
        }

        public String build() {
            return endpoint;
        }
        }


    protected Response postResponse(RequestSpecification requestSpecification, String endpoint) {
        return requestSpecification
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    protected RequestSpecification getRequestSpecification() {
        return given()
                .log().all()
                .contentType(APPLICATION_JSON_VALUE);
    }
}
