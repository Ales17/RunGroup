package com.rungroup.web.service;

import com.rungroup.web.dto.ClubDto;
import com.rungroup.web.models.Club;

import java.util.List;

public interface ClubService {
    List<ClubDto> findAllClubs();

    Club saveClub(ClubDto clubDto);

    ClubDto findClubById(Long clubId);

    void createClub(ClubDto club);

    void updateClub(ClubDto dto);

    void delete(Long clubId);

    List<ClubDto> searchClubs(String query);
}
