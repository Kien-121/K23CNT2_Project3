package com.giadungmart.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_logs")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class InventoryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantityChange;

    private LocalDateTime changedAt;

    private String note;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
