package com.mju.gwibi.controller;

import com.mju.gwibi.dto.response.FolderResponse;
import com.mju.gwibi.service.FolderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderApiController {
    private final FolderService folderService;

    @GetMapping
    public ResponseEntity<List<FolderResponse>> getAll() {
        return ResponseEntity.ok(folderService.getAll());
    }
}
