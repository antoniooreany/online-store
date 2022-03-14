package com.wasp.onlinestore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDateTime creationDate;
}
