package com.mju.gwibi.service;

import com.mju.gwibi.dto.response.FolderResponse;
import com.mju.gwibi.infrastructure.FolderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public List<FolderResponse> getAll() {
        return folderRepository.findAll().stream()
                .map(FolderResponse::of)
                .toList();
    }
}
