package com.aleksandrm.mynotions.events;

import com.aleksandrm.mynotions.repository.EventRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PageEventListener {
    private final EventRepository eventRepository;

    public PageEventListener(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @EventListener
    public void handlePageCreatedEvent(PageCreatedEvent event) {
        eventRepository.logEvent("PAGE_CREATED", event.ownerId(), event.metadata());
    }
}
