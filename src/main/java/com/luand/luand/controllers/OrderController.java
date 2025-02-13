package com.luand.luand.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luand.luand.entities.dto.order.CreateOrderDTO;
import com.luand.luand.entities.dto.order.OrderDetailsDTO;
import com.luand.luand.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

        private final OrderService orderService;

        @Operation(summary = "Retrieve a order by ID", description = "Fetch the details of a specific order using its unique identifier.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Order retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDetailsDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Order not found")
        })
        @GetMapping("/{id}")
        public ResponseEntity<OrderDetailsDTO> getOrderById(
                        @Parameter(description = "ID of the order to retrieve") @PathVariable Long id) {
                var order = orderService.getOrderById(id);
                return ResponseEntity.ok(new OrderDetailsDTO(order));
        }

        @Operation(summary = "Retrieve all orders", description = "Fetch a list of all orders.")
        @ApiResponse(responseCode = "200", description = "List of orders retrieved successfully")
        @GetMapping
        public ResponseEntity<List<OrderDetailsDTO>> getAllOrders() {
                var result = orderService.getAllOrders().stream().map(order -> new OrderDetailsDTO(order))
                                .collect(Collectors.toList());
                return ResponseEntity.ok(result);
        }

        @Operation(summary = "Create a new order", description = "Add a new order to the system.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Order created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDetailsDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid input data"),
                        @ApiResponse(responseCode = "409", description = "Name is already in use")
        })
        @PostMapping
        public ResponseEntity<OrderDetailsDTO> createOrder(@RequestBody @Valid CreateOrderDTO data) {
                var order = orderService.createOrder(data);
                return ResponseEntity.ok(new OrderDetailsDTO(order));
        }

        @Operation(summary = "Delete a order", description = "Remove a order from the system by its ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Order deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Order not found")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteOrder(
                        @Parameter(description = "ID of the order to delete") @PathVariable Long id) {
                orderService.deleteOrder(id);
                return ResponseEntity.noContent().build();
        }

}
