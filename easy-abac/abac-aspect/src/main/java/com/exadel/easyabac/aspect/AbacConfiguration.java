package com.exadel.easyabac.aspect;

import org.springframework.context.annotation.ImportResource;

/**
 * The configuration class for Spring Boot applications.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@org.springframework.context.annotation.Configuration
@ImportResource("classpath*:abac-config.xml")
public class AbacConfiguration {
}
