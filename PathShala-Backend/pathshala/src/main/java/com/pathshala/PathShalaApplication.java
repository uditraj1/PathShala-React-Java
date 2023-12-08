package com.pathshala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
@EntityScan("com.pathshala.dao")
public class PathShalaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PathShalaApplication.class, args);
    }

}
