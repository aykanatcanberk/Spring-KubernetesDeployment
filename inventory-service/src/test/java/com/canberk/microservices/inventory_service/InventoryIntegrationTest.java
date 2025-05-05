package com.canberk.microservices.inventory_service;

import com.canberk.microservices.inventory_service.model.Inventory;
import com.canberk.microservices.inventory_service.repository.InventoryRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class InventoryIntegrationTest {

    @Container
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest");

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    @LocalServerPort
    private int port;

    @Autowired
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;


        inventoryRepository.deleteAll();
        Inventory inventory = Inventory.builder()
                .code("Iphone_14")
                .quantity(20)
                .build();
        inventoryRepository.save(inventory);
    }

    @Test
    void shouldReturnTrueIfStockIsAvailable() {
        given()
                .queryParam("code", "Iphone_14")
                .queryParam("quantity", 10)
                .when()
                .get("/api/inventory")
                .then()
                .statusCode(200)
                .body(is("true"));
    }

    @Test
    void shouldReturnFalseIfStockIsNotAvailable() {
        given()
                .queryParam("code", "Iphone_14")
                .queryParam("quantity", 50)
                .when()
                .get("/api/inventory")
                .then()
                .statusCode(200)
                .body(is("false"));
    }
}