package com.eventhub.order.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacedEvent {

    private String orderId;
    private String customerName;
    private String customerEmail;
    private Double amount;
    private Instant timestamp;
}
