package com.aleksandrm.mynotions.controller;

import com.aleksandrm.mynotions.dto.*;
import com.aleksandrm.mynotions.model.EventQueryParams;
import com.aleksandrm.mynotions.service.EventsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventsController {
    private final EventsService eventsService;

    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping("/api/events")
    public ResponseEntity<PagedResponse<EventResponse>> getEvents(
            @Valid @ModelAttribute EventQueryParams query
    ) {
        PagedResponse<EventResponse> response = eventsService.getEvents(query);
        return ResponseEntity.ok(response);
    }
}
