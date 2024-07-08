package com.mju.gwibi.infrastructure;

import java.util.Optional;

import com.mju.gwibi.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.folder.id = :folderId")
    List<Category> findByFolderId(@Param("folderId") Long folderId);

}
