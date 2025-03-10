package com.satishlabs.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private PriceDTO price;
    private InventoryDTO inventory;
    private String category;
    private List<AttributeDTO> attributes;
    
   
}
