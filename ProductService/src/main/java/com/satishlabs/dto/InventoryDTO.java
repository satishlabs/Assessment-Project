package com.satishlabs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
	private Long productId;
    private int total;
    private int available;
    private int reserved;
    
    public InventoryDTO(int total, int available, int reserved) {
		super();
		this.total = total;
		this.available = available;
		this.reserved = reserved;
	}
    
    
}
