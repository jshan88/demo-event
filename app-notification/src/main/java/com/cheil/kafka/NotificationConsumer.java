package com.cheil.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class NotificationConsumer {
    @KafkaListener(topics = {"test-topic1"}, groupId = "testgroup1")
    public void consumerMessage(Object payload) {
        log.info("Received Message : {}", payload);
    }
}