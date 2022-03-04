package com.example.mall.note.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "data.source")
@Data
public class ChannelDataSourceConfig {
    private Map<String, String> channelMap = new HashMap<>();
}
