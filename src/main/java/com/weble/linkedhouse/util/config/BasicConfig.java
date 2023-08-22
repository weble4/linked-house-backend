package com.weble.linkedhouse.util.config;

import com.weble.linkedhouse.util.logtarce.LogTrace;
import com.weble.linkedhouse.util.logtarce.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
