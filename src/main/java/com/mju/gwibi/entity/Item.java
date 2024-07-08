package com.mju.gwibi.entity;

import static com.mju.gwibi.entity.Status.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String imageKey;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Item(Long id, String name, String imageKey, LocalDate startDate, LocalDate endDate, Category category) {
        this.id = id;
        this.imageKey = imageKey;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = getNowStatus(endDate);
        this.category = category;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void updateImageKey(String imageKey) {
        this.imageKey = imageKey;
    }
}
