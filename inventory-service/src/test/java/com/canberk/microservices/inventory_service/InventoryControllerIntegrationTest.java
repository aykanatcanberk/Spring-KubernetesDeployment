package com.canberk.microservices.inventory_service;

import com.canberk.microservices.inventory_service.model.Inventory;
import com.canberk.microservices.inventory_service.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@Testcontainers
public class InventoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("pass");

    @BeforeEach
    void setup() {
        inventoryRepository.deleteAll();
        Inventory item = new Inventory();
        item.setCode("ABC123");
        item.setQuantity(10);
        inventoryRepository.save(item);
    }

    @Test
    void shouldReturnTrueWhenStockIsAvailable() throws Exception {
        mockMvc.perform(get("/api/inventory")
                        .param("code", "ABC123")
                        .param("quantity", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void shouldReturnFalseWhenStockIsNotEnough() throws Exception {
        mockMvc.perform(get("/api/inventory")
                        .param("code", "ABC123")
                        .param("quantity", "15"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}