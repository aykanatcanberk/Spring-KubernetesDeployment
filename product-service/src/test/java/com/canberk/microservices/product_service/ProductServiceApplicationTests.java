package com.canberk.microservices.product_service;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import static org.hamcrest.Matchers.equalTo;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @LocalServerPort
    private int port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

    @Test
    void shouldCreateAndRetrieveProduct() {

        String requestBody = """
        {
            "name": "Smart Tv",
            "description": "Television",
            "price": 300
        }
    """;

        String productId = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/product")
                .then()
                .statusCode(201)
                .body("id" , Matchers.notNullValue())
                .body("name" , equalTo("Smart Tv"))
                .body("description", equalTo("Television"))
                .body("price" , equalTo(300)).toString();
    }


}
