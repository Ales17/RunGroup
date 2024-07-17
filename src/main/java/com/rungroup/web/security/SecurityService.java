package com.rungroup.web.security;

import com.rungroup.web.models.Club;
import com.rungroup.web.models.Event;
import com.rungroup.web.repository.ClubRepository;
import com.rungroup.web.repository.EventRepository;
import com.rungroup.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    UserRepository userRepository;
    ClubRepository clubRepository;
    EventRepository eventRepository;

    @Autowired
    public SecurityService(UserRepository userRepository, ClubRepository clubRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.clubRepository = clubRepository;
        this.eventRepository = eventRepository;
    }

    public boolean isClubOwner(long clubId) {
        Club club = clubRepository.findById(clubId).get();
        String ownerUsername = club.getCreatedBy().getUsername();
        String sessionUsername = SecurityUtil.getSessionUser();
        return ownerUsername.equals(sessionUsername);
    }

    public boolean isEventOwner(long eventId) {
        Event event = eventRepository.findById(eventId).get();
        String ownerUsername = event.getClub().getCreatedBy().getUsername();
        String sessionUsername = SecurityUtil.getSessionUser();
        return ownerUsername.equals(sessionUsername);
    }
}
