package com.aleksandrm.mynotions.service;

import com.aleksandrm.mynotions.model.EventFilter;
import com.aleksandrm.mynotions.dto.EventResponse;
import com.aleksandrm.mynotions.dto.PagedResponse;
import com.aleksandrm.mynotions.model.EventQueryParams;
import com.aleksandrm.mynotions.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsService {
    private final EventRepository eventRepository;
    private final CurrentUserProvider currentUserProvider;

    public EventsService(EventRepository eventRepository, CurrentUserProvider currentUserProvider) {
        this.eventRepository = eventRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public PagedResponse<EventResponse> getEvents(EventQueryParams queryParams) {
        Long userId = currentUserProvider.getCurrentUserId();

        EventFilter filter = new EventFilter(
                queryParams.type(),
                queryParams.entityType(),
                queryParams.entityId(),
                queryParams.from(),
                queryParams.to()
        );

        int page = queryParams.pageOrDefault();
        int pageSize = queryParams.pageSizeOrDefault();
        int offset = page * pageSize;

        List<EventResponse> events = eventRepository.findEvents(userId, filter, pageSize, offset);
        long total = eventRepository.countEvents(userId, filter);
        int totalPages = (int) Math.ceil((double) total / pageSize);
        boolean last = page >= Math.max(totalPages - 1, 0);

        return new PagedResponse<>(events, page, pageSize, (int) total, totalPages, last);
    }
}
