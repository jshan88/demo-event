package com.cheil.user;

import com.cheil.producer.KafkaProducer;
import com.cheil.user.dto.UserJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("test")
public class TestController {

    private final KafkaProducer kafkaProducer;

    @PostMapping(path = "/Object")
    public void testKafka(@RequestBody UserJoinRequest userJoinRequest){
        kafkaProducer.produceMessage("test-topic1", userJoinRequest);
    }
}
