package com.weble.linkedhouse.util.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "ncp")
public class NaverProperties {

    private String accessKey;
    private String secretKey;
    private String regionName;
    private String endPoint;
}

