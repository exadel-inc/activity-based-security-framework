package com.exadel.easyabac.demo;

import com.exadel.easyabac.aspect.AbacConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Demo application.
 * Use {@code @ImportResource("classpath*:abac-config.xml")}
 * for non-Spring Boot applications.
 *
 * @author Igor Sych
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@SpringBootApplication
@Import(AbacConfiguration.class)
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
