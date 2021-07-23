package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix ="txy" )
@Data
public class TXYConfig {

    String secretId ;

    String tableName;
    String secretKey;
    String region;
}
