package com.aitem.connect.repository.eventhandler;

import com.aitem.connect.repository.UserHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RepositoryConfiguration {

    private UserHistoryRepository historyRepository;

    public RepositoryConfiguration(@Autowired UserHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Bean
    UserEventHandler authorEventHandler() {
        return new UserEventHandler();
    }
}