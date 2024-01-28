package com.example3.utility;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SessionCreator {

    // fetching hibernate config file from application.properties file
    @Value("${db.configFile.name}")
    private String DBConfigFile;

    // method to get the session factory based on the configuration provided in the hibernate config file
    @Bean
    public SessionFactory getSessionFactory() {
        Configuration config = new Configuration().configure(DBConfigFile);
        SessionFactory sessionFactory = config.buildSessionFactory();
        return sessionFactory;
    }
}
