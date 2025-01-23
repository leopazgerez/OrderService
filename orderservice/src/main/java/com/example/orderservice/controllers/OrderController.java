package com.example.orderservice.controllers;

import com.example.orderservice.dtos.OrderDTO;
import com.example.orderservice.services.OrderService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Operation(summary = "Retrieve user by ID", description = "Fetches all products.")
    @GetMapping("/all")
    ResponseEntity<?> getAllProduct() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Operation(summary = "Retrieve user by ID", description = "Update product.")
    @PutMapping("/update")
//    agregar @Valid luego de testear
    ResponseEntity<?> updateProduct(@PathVariable Long id) throws BadRequestException {
        orderService.update(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Retrieve user by ID", description = "Create product.")
    @PostMapping("/create")
        //    agregar @Valid luego de testear
    ResponseEntity<?> createProduct(@RequestBody OrderDTO productDTO) throws BadRequestException {
        return ResponseEntity.ok(orderService.create(productDTO));
    }
}
