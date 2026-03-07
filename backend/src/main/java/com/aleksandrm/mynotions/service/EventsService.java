package com.aleksandrm.mynotions.service;

import com.aleksandrm.mynotions.model.EventFilter;
import com.aleksandrm.mynotions.dto.EventResponse;
import com.aleksandrm.mynotions.dto.PagedResponse;
import com.aleksandrm.mynotions.model.EventQueryParams;
import com.aleksandrm.mynotions.repository.EventRepository;
import com.aleksandrm.mynotions.security.PrincipalUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsService {
    private final EventRepository eventRepository;

    public EventsService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public PagedResponse<EventResponse> getEvents(EventQueryParams queryParams) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser user = (PrincipalUser) auth.getPrincipal();

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

        List<EventResponse> events = eventRepository.findEvents(user.getId(), filter, pageSize, offset);
        long total = eventRepository.countEvents(user.getId(), filter);
        int totalPages = (int) Math.ceil((double) total / pageSize);
        boolean last = page >= Math.max(totalPages - 1, 0);

        return new PagedResponse<>(events, page, pageSize, (int) total, totalPages, last);
    }
}
