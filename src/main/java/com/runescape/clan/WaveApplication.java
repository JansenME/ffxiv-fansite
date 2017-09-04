package com.runescape.clan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class WaveApplication extends SpringBootServletInitializer {
    private static final Logger ownLogger = LoggerFactory.getLogger(WaveApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        ownLogger.info("I'm in the configure method.");
        return application.sources(WaveApplication.class);
    }

    public static void main(String[] args) {
        ownLogger.info("I'm going for the main!");
        SpringApplication.run(WaveApplication.class, args);
    }
}
