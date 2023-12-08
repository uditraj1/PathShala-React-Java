package com.pathshala.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PropertyConfig {
    private final Environment env;

    public PropertyConfig(Environment env) {
        this.env = env;
    }

    public String getPropertyByName(String propertyName){
        return env.getProperty(propertyName);
    }
}
