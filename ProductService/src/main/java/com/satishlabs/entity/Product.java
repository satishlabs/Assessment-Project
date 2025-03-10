package com.satishlabs.entity;

import java.util.List;

import com.satishlabs.dto.PriceDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Product name is required")
	private String name;

	@NotBlank(message = "Brand is required")
	private String brand;

	private String description;

	@Embedded
	private Price price;

	@Embedded
	private Inventory inventory;

	@NotBlank(message = "Category is required")
	private String category;

	@ElementCollection
	private List<ProductAttribute> attributes;
}
