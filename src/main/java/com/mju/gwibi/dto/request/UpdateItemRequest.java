package com.mju.gwibi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
public class UpdateItemRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String newName;

    @NotNull
    @Min(1)
    private Integer newCycle;

    @NotNull
    private Boolean isImageChanged;

    private String originalImageKey;

}
