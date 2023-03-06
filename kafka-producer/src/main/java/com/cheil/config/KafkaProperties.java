package com.cheil.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@NoArgsConstructor
@Getter
@PropertySource("classpath:application-producer.yml")
public class KafkaProperties {

    @Value("${kafka.bootstrap-servers}")
    private String bootStrapServers;
}
