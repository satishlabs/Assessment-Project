package com.satishlabs.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    private Long productId;
    private int total;
    private int available;
    private int reserved;
	public Inventory(int total, int available, int reserved) {
		super();
		this.total = total;
		this.available = available;
		this.reserved = reserved;
	}
    
    
}
