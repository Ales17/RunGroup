package com.rungroup.web.controller;

import com.rungroup.web.dto.EventDto;
import com.rungroup.web.models.Event;
import com.rungroup.web.models.EventType;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.EventService;
import com.rungroup.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EventController {
    EventService eventService;
    UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addTypesToModel(Model model) {
        List<EventType> types = eventService.findAllEventTypes();
        model.addAttribute("types", types);
    }

    @GetMapping("/events")
    public String listEvents(Model model) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);

        List<EventDto> events = eventService.findAllEvents();
        model.addAttribute("events", events);
        return "events-list";
    }

    @GetMapping("/events/{eventId}/edit")
    @PreAuthorize("@securityService.isEventOwner(#eventId)")
    public String editEventForm(@PathVariable("eventId") Long eventId, Model model) {
        EventDto eventDto = eventService.findByEventId(eventId);
        model.addAttribute("event", eventDto);
        model.addAttribute("type_id", eventDto.getType().getId());
        return "events-edit";
    }

    @PostMapping("/events/{eventId}/edit")
    @PreAuthorize("@securityService.isEventOwner(#eventId)")
    public String updateEvent(@PathVariable("eventId") Long eventId,
                              @Valid @ModelAttribute("event") EventDto event,
                              @RequestParam("eventType") Long type,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("event", event);
            model.addAttribute("event", event);
            event.setId(eventId);
            return "events-edit";
        }
        EventDto eventDto = eventService.findByEventId(eventId);
        event.setId(eventId);
        event.setClub(eventDto.getClub());
        eventService.updateEvent(event, type);
        return "redirect:/events/" + eventId;
    }

    @GetMapping("events/{eventId}/delete")
    @PreAuthorize("@securityService.isEventOwner(#eventId)")
    public String deleteEvent(@PathVariable("eventId") Long eventId) {
        eventService.deleteEvent(eventId);
        return "redirect:/events";
    }

    @GetMapping("/events/{eventId}")
    public String viewEvent(@PathVariable("eventId") Long eventId, Model model) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);

        EventDto eventDto = eventService.findByEventId(eventId);
        model.addAttribute("event", eventDto);
        return "events-detail";
    }

    @PostMapping("events/{clubId}/new")
    public String createEventNew(@PathVariable("clubId") Long clubId,
                                 @Valid @ModelAttribute("event") EventDto eventDto,
                                 @RequestParam("eventType") Long type,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("event", eventDto);
            return "events-create";
        }
        eventService.createEvent(clubId, eventDto, type);
        return "redirect:/clubs/" + clubId;
    }

    @GetMapping("events/{clubId}/new")
    public String createEventFormNew(@PathVariable("clubId") Long clubId, Model model) {
        Event event = new Event();
        model.addAttribute("clubId", clubId);
        model.addAttribute("event", event);
        return "events-create";
    }


}
