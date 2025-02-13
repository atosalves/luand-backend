package com.luand.luand.entities.dto.order;

import java.util.Set;

import com.luand.luand.entities.OrderItem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateOrderDTO(
                @NotBlank(message = "Client name is required") @Size(min = 3, max = 20, message = "Client name must be between 3 and 20 characters") String client,
                @NotEmpty(message = "At least one size is required") Set<OrderItem> orderItems,
                @NotNull(message = "Store is required") Long storeId) {

}
