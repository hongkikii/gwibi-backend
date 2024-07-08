package com.mju.gwibi.infrastructure;

import com.mju.gwibi.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
}
