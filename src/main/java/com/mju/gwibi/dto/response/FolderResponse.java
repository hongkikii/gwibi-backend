package com.mju.gwibi.dto.response;

import com.mju.gwibi.entity.Folder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FolderResponse {
    private final Long id;
    private final String name;
    private final String imageKey;

    public static FolderResponse of(Folder folder) {
        return FolderResponse.builder()
                .id(folder.getId())
                .name(folder.getName())
                .imageKey(folder.getImageKey())
                .build();
    }
}
