package com.rungroup.web.controller;

import com.rungroup.web.dto.ClubDto;
import com.rungroup.web.models.Club;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.ClubService;
import com.rungroup.web.service.StorageService;
import com.rungroup.web.service.UserService;
import com.rungroup.web.storage.FileUtil;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ClubController {
    private final ClubService clubService;
    private final UserService userService;
    private final StorageService storageService;
    // Helper for club form validation
    private boolean isImageValid = false;

    public ClubController(ClubService clubService, UserService userService, StorageService storageService) {
        this.clubService = clubService;
        this.userService = userService;
        this.storageService = storageService;
    }

    private void addUserToModel(Model model) {
        String username = SecurityUtil.getSessionUser();
        if (username == null) return;
        UserEntity user = userService.findByUsername(username);
        model.addAttribute("user", user);
    }

    @GetMapping({"/clubs", "/"})
    public String listClubs(Model model) {
        addUserToModel(model);

        List<ClubDto> clubs = clubService.findAllClubs();
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }

    @GetMapping("/clubs/{clubId}")
    public String clubDetail(Model model,
                             @PathVariable("clubId") long clubId) {
        addUserToModel(model);

        ClubDto clubDto = clubService.findClubById(clubId);
        model.addAttribute("club", clubDto);
        return "clubs-detail";
    }

    @GetMapping("/clubs/search")
    public String searchClubs(@RequestParam("q") String query, Model model) {
        addUserToModel(model);
        List<ClubDto> clubs = clubService.searchClubs(query);
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }

    @GetMapping("/clubs/new")
    public String createClubForm(Model model) {
        Club club = new Club();
        model.addAttribute("club", club);
        return "clubs-create";
    }

    @PostMapping(value = "/clubs/new", consumes = "multipart/form-data")
    public String saveClub(Model model, @Valid @ModelAttribute("club") ClubDto clubDto, BindingResult result, @RequestParam("photo") MultipartFile file, RedirectAttributes attributes) {

        // Allow user to create a club without image
        if (!file.isEmpty()) {
            try {
                String uploadedFileName = storageService.store(file);
                clubDto.setPhotoUrl(FileUtil.ROOT_LOCATION + uploadedFileName);
                isImageValid = true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                model.addAttribute("club", clubDto);
                model.addAttribute("message", "Please upload a valid image file.");
                return "clubs-create";
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("club", clubDto);
            if (isImageValid)
                model.addAttribute("message", "Due to security reasons, please upload your pic again.");
            return "clubs-create";
        }
        Club c = clubService.saveClub(clubDto);
        return "redirect:/clubs/" + c.getId();
    }

    @GetMapping("/clubs/{clubId}/edit")
    @PreAuthorize("@securityService.isClubOwner(#clubId)")
    public String editClub(@PathVariable("clubId") Long clubId, Model model) {
        ClubDto clubDto = clubService.findClubById(clubId);
        model.addAttribute("club", clubDto);
        return "clubs-edit";
    }

    @PostMapping(value = "/clubs/{clubId}/edit", consumes = "multipart/form-data")
    @PreAuthorize("@securityService.isClubOwner(#clubId)")
    // It is important to put attribute name behind @ModelAttribute("attributeName")
    public String updateClub(@PathVariable Long clubId, Model model, @Valid @ModelAttribute("club") ClubDto club,
                             BindingResult result, @RequestParam("photo") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String uploadedFileName = storageService.store(file);
                club.setPhotoUrl(FileUtil.ROOT_LOCATION + uploadedFileName);
                isImageValid = true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                model.addAttribute("club", club);
                model.addAttribute("message", "Please upload a valid image file.");
                return "clubs-edit";
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("club", club);
            if (isImageValid)
                model.addAttribute("message", "Due to security reasons, please upload your pic again.");

            return "clubs-edit";
        }
        club.setId(clubId);
        clubService.updateClub(club);
        return "redirect:/clubs/" + clubId;
    }

    @GetMapping("/clubs/{clubId}/delete")
    @PreAuthorize("@securityService.isClubOwner(#clubId)")
    public String deleteClub(@PathVariable("clubId") Long clubId) {
        clubService.delete(clubId);
        return "redirect:/clubs";
    }
}
