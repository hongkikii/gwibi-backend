package com.mju.gwibi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DeleteItemRequest {
    @NotNull
    private Long id;

    private String imageKey;
}
