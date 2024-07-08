package com.mju.gwibi.dto.response;

import com.mju.gwibi.entity.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponse {
    private final Long id;
    private final String name;
    private final Integer usagePeriod;
    private final String imageKey;

    public static CategoryResponse of(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .usagePeriod(category.getUsagePeriod())
                .imageKey(category.getImageKey())
                .build();
    }
}
