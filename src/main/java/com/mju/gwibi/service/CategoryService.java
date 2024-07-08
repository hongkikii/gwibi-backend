package com.mju.gwibi.service;

import com.mju.gwibi.dto.response.CategoryResponse;
import com.mju.gwibi.entity.Category;
import com.mju.gwibi.infrastructure.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::of)
                .toList();
    }

    public Category getByName(String name){
        return categoryRepository.findByName(name).orElseThrow();
    }

    public List<CategoryResponse> getByFolderId(Long id) {
        return categoryRepository.findByFolderId(id).stream()
                .map(CategoryResponse::of)
                .toList();
    }
}
