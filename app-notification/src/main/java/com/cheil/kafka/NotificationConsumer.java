package com.cheil.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationConsumer {


    @Setter
    private static class Payload {
        private String email;
        private String firstName;
        private String lastName;
    }

    @KafkaListener(topics = {"test-topic1"}, groupId = "testgroup1")
    public void consumerMessage(Payload payload) {
        log.info("###### Received Message : {}", payload.email);
    }
}
