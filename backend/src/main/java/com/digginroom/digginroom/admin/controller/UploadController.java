package com.digginroom.digginroom.admin.controller;

import com.digginroom.digginroom.admin.controller.dto.UploadRequest;
import com.digginroom.digginroom.admin.service.UploadService;
import com.digginroom.digginroom.domain.Genre;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @ModelAttribute("genre")
    public Genre[] genreCategories() {
        return Genre.values();
    }

    @GetMapping("/upload")
    public String upload(final Model model, final UploadRequest request) {
        model.addAttribute("request", request);
        return "uploadForm";
    }

    @PostMapping("/upload")
    public ResponseEntity<Void> upload(@Valid @ModelAttribute("request") final UploadRequest request) {
        uploadService.save(request);

        return ResponseEntity.ok().build();
    }
}
