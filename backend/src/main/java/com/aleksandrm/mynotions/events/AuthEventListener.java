package com.aleksandrm.mynotions.events;

import com.aleksandrm.mynotions.repository.EventRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuthEventListener {
    private final EventRepository eventRepository;

    public AuthEventListener(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @EventListener
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        eventRepository.logEvent("USER_REGISTERED", event.userId(), "{}");
    }

    @EventListener
    public void handleUserLoggedEvent(UserLoggedEvent event) {
        eventRepository.logEvent("USER_LOGGED", event.userId(), "{}");
    }
}
