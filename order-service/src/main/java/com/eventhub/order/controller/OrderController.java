package com.eventhub.order.controller;

import com.eventhub.order.dto.PlaceOrderRequest;
import com.eventhub.order.event.OrderPlacedEvent;
import com.eventhub.order.kafka.OrderEventProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderEventProducer orderEventProducer;

    @PostMapping
    public ResponseEntity<Map<String, Object>> placeOrder(@Valid @RequestBody PlaceOrderRequest request) {
        String orderId = UUID.randomUUID().toString();

        OrderPlacedEvent event = new OrderPlacedEvent(
                orderId,
                request.getCustomerName(),
                request.getCustomerEmail(),
                request.getAmount(),
                Instant.now()
        );

        orderEventProducer.publishOrderPlacedEvent(event);

        log.info("Order placed orderId={} customer={}", orderId, request.getCustomerName());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "orderId", orderId,
                "message", "Order placed successfully",
                "status", "PUBLISHED_TO_KAFKA"
        ));
    }
}
