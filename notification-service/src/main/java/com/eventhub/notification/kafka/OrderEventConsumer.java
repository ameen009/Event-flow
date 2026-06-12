package com.eventhub.notification.kafka;

import com.eventhub.notification.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderEventConsumer {

    @Value("${server.port}")
    private int serverPort;

    @KafkaListener(topics = "orders-topic", groupId = "notification-group")
    public void consume(ConsumerRecord<String, OrderPlacedEvent> record) {
        OrderPlacedEvent event = record.value();
        log.info("==============================================");
        log.info("  [Instance :{}]  Received OrderPlacedEvent", serverPort);
        log.info("  Partition   : {}", record.partition());
        log.info("  Offset      : {}", record.offset());
        log.info("  Order ID    : {}", event.getOrderId());
        log.info("  Customer    : {}", event.getCustomerName());
        log.info("  Email       : {}", event.getCustomerEmail());
        log.info("  Amount      : ${}", event.getAmount());
        log.info("==============================================");

        sendEmailNotification(event);
    }

    private void sendEmailNotification(OrderPlacedEvent event) {
        log.info("[Instance :{}] Sending email to {} for order {}",
                serverPort, event.getCustomerEmail(), event.getOrderId());
    }
}
