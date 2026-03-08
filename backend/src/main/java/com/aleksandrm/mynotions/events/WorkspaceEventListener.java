package com.aleksandrm.mynotions.events;

import com.aleksandrm.mynotions.repository.EventRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceEventListener {
    private final EventRepository eventRepository;

    public WorkspaceEventListener(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @EventListener
    public void handleWorkspaceCreatedEvent(WorkspaceCreatedEvent event) {
        eventRepository.logEvent("WORKSPACE_CREATED", event.ownerId(), event.metadata());
    }

    @EventListener
    public void handleWorkspaceUpdatedEvent(WorkspaceUpdatedEvent event) {
        eventRepository.logEvent("WORKSPACE_UPDATED", event.ownerId(), event.metadata());
    }

    @EventListener
    public void handleWorkspaceDeletedEvent(WorkspaceDeletedEvent event) {
        eventRepository.logEvent("WORKSPACE_DELETED", event.ownerId(), event.metadata());
    }
}
