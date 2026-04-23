package com.session13.ex05.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for Hibernate SessionFactory
 * The OpenSessionInView is already enabled via spring.jpa.open-in-view=true in application.properties
 * This helps resolve LazyInitializationException by keeping the Session open during view rendering
 */
@Configuration
public class HibernateConfig implements WebMvcConfigurer {
    // Configuration is handled by spring.jpa.open-in-view=true property
}


