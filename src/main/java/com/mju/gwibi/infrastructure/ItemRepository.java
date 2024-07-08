package com.mju.gwibi.infrastructure;

import com.mju.gwibi.entity.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.category.id = :categoryId")
    List<Item> findByCategoryId(@Param("categoryId") Long categoryId);
}
