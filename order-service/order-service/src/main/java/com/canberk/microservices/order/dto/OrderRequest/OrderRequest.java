package com.canberk.microservices.order.dto.OrderRequest;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record OrderRequest(
        Long id,

        String orderNumber,

        @NotBlank(message = "Code cannot be blank")
        String code,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than 0")
        Integer quantity
) {
}
