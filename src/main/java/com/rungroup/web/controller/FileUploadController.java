package com.rungroup.web.controller;

import com.rungroup.web.service.StorageService;
import com.rungroup.web.storage.StorageFileNotFoundException;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.stream.Collectors;

import static com.rungroup.web.storage.FileUtil.allowedFileTypes;
import static org.apache.commons.io.FilenameUtils.getExtension;


@RequestMapping("/files")
@Controller
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RolesAllowed("ADMIN")
    @GetMapping("")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "fragments/admin :: uploads";
    }

    @GetMapping(value = "{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "inline").header(HttpHeaders.CONTENT_TYPE, allowedFileTypes.get(getExtension(filename))).body(file);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(StorageFileNotFoundException.class)
    public String handleStorageFileNotFound(Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("description", "We could not find this file.");
        return "custom-error";
    }

}