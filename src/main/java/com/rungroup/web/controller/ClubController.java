package com.rungroup.web.controller;

import com.rungroup.web.dto.ClubDto;
import com.rungroup.web.models.Club;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.security.SecurityService;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.ClubService;
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
public class ClubController {
    private ClubService clubService;
    private UserService userService;
    private SecurityService securityService;
    @Autowired
    public ClubController(ClubService clubService, UserService userService, SecurityService securityService) {
        this.clubService = clubService;
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping({"/clubs", "/"})
    public String listClubs(Model model) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);

        List<ClubDto> clubs = clubService.findAllClubs();
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }

    @GetMapping("/clubs/{clubId}")
    public String clubDetail(Model model,
                             @PathVariable("clubId") long clubId) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);

        ClubDto clubDto = clubService.findClubById(clubId);
        model.addAttribute("club", clubDto);
        return "clubs-detail";
    }

    @GetMapping("/clubs/new")
    public String createClubForm(Model model) {
        Club club = new Club();
        model.addAttribute("club", club);
        return "clubs-create";
    }

    @GetMapping("/clubs/search")
    public String searchClubs(@RequestParam("query") String query, Model model) {
        List<ClubDto> clubs = clubService.searchClubs(query);
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }

    @PostMapping("/clubs/new")
    public String saveClub(Model model, @Valid @ModelAttribute("club") ClubDto clubDto, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("club", clubDto);
            return "clubs-create";
        }
        clubService.saveClub(clubDto);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/edit")
    @PreAuthorize("@securityService.isClubOwner(#clubId)")
    public String editClub(@PathVariable("clubId") Long clubId, Model model) {
        ClubDto clubDto = clubService.findClubById(clubId);
        model.addAttribute("club", clubDto);
        return "clubs-edit";
    }

    @PostMapping("/clubs/{clubId}/edit")
    @PreAuthorize("@securityService.isClubOwner(#clubId)")
    // It is important to put attribute name behind @ModelAttribute("attributeName")
    public String updateClub(@PathVariable Long clubId, Model model, @Valid @ModelAttribute("club") ClubDto club,
                             BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("club", club);
            return "clubs-edit";
        }
        club.setId(clubId);
        clubService.updateClub(club);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/delete")
    @PreAuthorize("@securityService.isClubOwner(#clubId)")
    public String deleteClub(@PathVariable("clubId") Long clubId) {
        clubService.delete(clubId);
        return "redirect:/clubs";
    }
}
