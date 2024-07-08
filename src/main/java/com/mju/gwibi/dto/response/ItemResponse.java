package com.mju.gwibi.dto.response;

import com.mju.gwibi.entity.Item;
import com.mju.gwibi.entity.Status;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemResponse {
    private final Long id;
    private final String name;
    private final Status status;
    private final Long categoryId;
    private final String imageKey;

    public static ItemResponse of(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .status(item.getStatus())
                .categoryId(item.getCategory().getId())
                .imageKey(item.getImageKey())
                .build();
    }
}
