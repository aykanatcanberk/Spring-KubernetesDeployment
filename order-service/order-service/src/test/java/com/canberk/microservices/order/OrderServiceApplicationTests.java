package com.canberk.microservices.order;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
@TestPropertySource(properties = "spring.flyway.enabled=true")
class OrderServiceApplicationTests {

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	void shouldCreateOrderSuccessfully() {
		String requestBody = """
        {
            "code": "ORDER-1001",
            "price": 250.75,
            "quantity": 3
        }
    """;

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/order")
				.then()
				.statusCode(201)
				.body("code", equalTo("ORDER-1001"))
				.body("price", equalTo(250.75f))
				.body("quantity", equalTo(3));
	}

	@Test
	void shouldFailWhenCodeIsMissing() {
		String requestBody = """
        {
            "code": "",
            "price": 100.0,
            "quantity": 2
        }
    """;

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/order")
				.then()
				.statusCode(400);
	}

	@Test
	void shouldFailWhenPriceIsNegative() {
		String requestBody = """
        {
            "code": "ORDER-NEG",
            "price": -50.0,
            "quantity": 1
        }
    """;

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/order")
				.then()
				.statusCode(400);
	}

	@Test
	void shouldFailWhenQuantityIsMissing() {
		String requestBody = """
        {
            "code": "ORDER-NULLQ",
            "price": 99.9
        }
    """;

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/order")
				.then()
				.statusCode(400);
	}




}
