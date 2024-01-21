package com.example2.utility;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SessionCreator {

    @Value("${db.configFile.name}")
    private String DBConfigFile;

    @Bean
    public SessionFactory getSessionFactory() {
        Configuration config = new Configuration().configure(DBConfigFile);
        SessionFactory sessionFactory = config.buildSessionFactory();
        return sessionFactory;
    }
}
