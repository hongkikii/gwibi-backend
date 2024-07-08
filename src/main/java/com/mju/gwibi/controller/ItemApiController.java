package com.mju.gwibi.controller;

import com.mju.gwibi.dto.request.CreateItemRequest;
import com.mju.gwibi.dto.request.DeleteItemRequest;
import com.mju.gwibi.dto.request.UpdateItemRequest;
import com.mju.gwibi.dto.response.ItemResponse;
import com.mju.gwibi.service.ItemService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/items")
public class ItemApiController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponse> create(@Valid @RequestPart(value = "request") CreateItemRequest request,
                                               @RequestPart(value = "file") MultipartFile file) {
        ItemResponse response = itemService.create(request, file);
        URI location = URI.create("/items" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    // TODO : 간단하게라도 사용자 로그인 만들어야 하나?
    @GetMapping
    public ResponseEntity<List<ItemResponse>> get() {
        return ResponseEntity.ok(itemService.get());
    }

    @PatchMapping
    public ResponseEntity<ItemResponse> update(@Valid @RequestPart(value = "request") UpdateItemRequest request,
                                               @RequestPart(value = "file")MultipartFile file) {
        return ResponseEntity.ok(itemService.update(request, file));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@Valid @RequestBody DeleteItemRequest request) {
        itemService.delete(request);
        return ResponseEntity.noContent().build();
    }
}
