package com.media_collection.frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BackendConfig {
    @Value("${backend.url}")
    public String backendUrl;
}
