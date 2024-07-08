package com.mju.gwibi.service;

import static com.mju.gwibi.entity.Status.getNowStatus;
import static java.util.Objects.isNull;

import com.mju.gwibi.dto.request.CreateItemRequest;
import com.mju.gwibi.dto.request.DeleteItemRequest;
import com.mju.gwibi.dto.request.UpdateItemRequest;
import com.mju.gwibi.dto.response.ItemResponse;
import com.mju.gwibi.entity.Item;
import com.mju.gwibi.infrastructure.CategoryRepository;
import com.mju.gwibi.infrastructure.ItemRepository;
import com.mju.gwibi.infrastructure.S3Uploader;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ItemResponse create(CreateItemRequest request, MultipartFile file) {
        LocalDate startDate = LocalDate.now();

        Item savedItem = itemRepository.save(Item.builder()
                .name(request.getName())
                .startDate(startDate)
                .endDate(startDate.plusDays(request.getCycle()))
                .category(categoryRepository.getReferenceById(request.getCategoryId()))
                .build());

        String imageKey = s3Uploader.put(savedItem.getId(), file);
        savedItem.updateImageKey(imageKey);
        return ItemResponse.of(savedItem);
    }

    public List<ItemResponse> get() {
        return itemRepository.findAll().stream()
                .map(ItemResponse::of)
                .toList();
    }

    public List<ItemResponse> getByCategoryId(Long id) {
        return itemRepository.findByCategoryId(id).stream()
                .map(ItemResponse::of)
                .toList();
    }

    public ItemResponse update(UpdateItemRequest request, MultipartFile file) {
        Item originalItem = itemRepository.getReferenceById(request.getId());
        Long itemId = originalItem.getId();
        LocalDate startDate = originalItem.getStartDate();

        Item updatedItem = itemRepository.save(Item.builder()
                                    .id(itemId)
                                    .name(request.getNewName())
                                    .startDate(startDate)
                                    .endDate(startDate.plusDays(request.getNewCycle()))
                                    .category(originalItem.getCategory())
                                    .imageKey(originalItem.getImageKey())
                                    .build());

        if(request.getIsImageChanged()) {
            s3Uploader.delete(request.getOriginalImageKey());
            String newImageKey = s3Uploader.put(originalItem.getId(), file);
            updatedItem.updateImageKey(newImageKey);
        }
        return ItemResponse.of(updatedItem);
    }

    @Transactional
    public void delete(DeleteItemRequest request) {
        if(!isNull(request.getImageKey()))
           s3Uploader.delete(request.getImageKey());
        itemRepository.deleteById(request.getId());
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateStatus() {
        List<Item> items = itemRepository.findAll();
        items.stream().forEach(item -> {
            item.updateStatus(getNowStatus(item.getEndDate()));
        });
        itemRepository.saveAll(items);
    }
}
