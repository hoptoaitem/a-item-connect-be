package com.aitem.connect.repository.eventhandler;

import com.aitem.connect.model.User;
import com.aitem.connect.model.UserHistory;
import com.aitem.connect.repository.UserHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RepositoryEventHandler ({User.class})
public class UserEventHandler {

    @Autowired
    private UserHistoryRepository historyRepository;

    @HandleBeforeCreate
    public void handleAuthorBefore(User user) {
        updateHistory(user);
    }
    @HandleAfterSave(User.class)
    public void handleAfterCreate(User user) {
        updateHistory(user);
    }

    private void updateHistory(User user) {
        UserHistory history = new UserHistory();
        history.setId(UUID.randomUUID().toString());
        history.setUserId(user.getId());
        historyRepository.save(history);
    }
}