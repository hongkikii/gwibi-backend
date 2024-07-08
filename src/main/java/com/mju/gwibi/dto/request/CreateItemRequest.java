package com.mju.gwibi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateItemRequest {
    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    private Integer cycle;

    @NotNull
    private Long categoryId;

    @Builder
    public CreateItemRequest(String name, Integer cycle, Long categoryId) {
        this.name = name;
        this.cycle = cycle;
        this.categoryId = categoryId;
    }
}
