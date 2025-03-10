package com.satishlabs.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.satishlabs.dto.AttributeDTO;
import com.satishlabs.dto.InventoryDTO;
import com.satishlabs.dto.ProductDTO;
import com.satishlabs.entity.Inventory;
import com.satishlabs.entity.Price;
import com.satishlabs.entity.Product;
import com.satishlabs.entity.ProductAttribute;
import com.satishlabs.proxy.InventoryProxy;
import com.satishlabs.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final InventoryProxy inventoryProxy;
	private final ModelMapper modelMapper;

	public List<ProductDTO> getProductsByCategory(String category, String sortBy) {
		List<Product> products = productRepository.findByCategoryAndInventoryAvailableGreaterThan(category, 0);

		if (products.isEmpty()) {
			throw new EntityNotFoundException("No products found in this category");
		}

		if ("price".equalsIgnoreCase(sortBy)) {
			products.sort(Comparator.comparing(p -> p.getPrice().getAmount()));
		} else if ("availability".equalsIgnoreCase(sortBy)) {
			products.sort(Comparator.comparing(p -> p.getInventory().getAvailable(), Comparator.reverseOrder()));
		}

		return products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.collect(Collectors.toList());
	}

	public ProductDTO addProduct(ProductDTO productDTO) {
		Product product = new Product();
		product.setName(productDTO.getName());
		product.setBrand(productDTO.getBrand());
		product.setDescription(productDTO.getDescription());
		if (productDTO.getPrice() != null) {
			Price price = new Price();
			price.setCurrency(productDTO.getPrice().getCurrency());
			price.setAmount(productDTO.getPrice().getAmount());
			product.setPrice(price);
		}

		if (productDTO.getInventory() != null) {
			Inventory inventory = new Inventory();
			inventory.setTotal(productDTO.getInventory().getTotal());
			inventory.setAvailable(productDTO.getInventory().getAvailable());
			inventory.setReserved(productDTO.getInventory().getReserved());
			product.setInventory(inventory);
		}
		product.setCategory(productDTO.getCategory());
		if (productDTO.getAttributes() != null) {
			product.setAttributes(convertAttributes(productDTO.getAttributes()));
		}
		productRepository.save(product);
		InventoryDTO inventoryDTO = new InventoryDTO(product.getId(), product.getInventory().getTotal(),
				product.getInventory().getAvailable(), product.getInventory().getReserved());
		try {
			ResponseEntity<Void> response = inventoryProxy.addInventory(inventoryDTO);
			System.out.println("Inventory Service Response: " + response.getStatusCode());
		} catch (Exception e) {
			System.err.println("Failed to add inventory for product ID: " + product.getId() + " - " + e.getMessage());
		}

		return modelMapper.map(product, ProductDTO.class);
	}

	public void deleteProduct(Long productId) {
		productRepository.deleteById(productId);
	}

	public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product not found"));

		if (productDTO.getName() != null) {
			product.setName(productDTO.getName());
		}
		if (productDTO.getBrand() != null) {
			product.setBrand(productDTO.getBrand());
		}
		if (productDTO.getDescription() != null) {
			product.setDescription(productDTO.getDescription());
		}

		if (productDTO.getPrice() != null) {
			product.getPrice().setCurrency(productDTO.getPrice().getCurrency());
			product.getPrice().setAmount(productDTO.getPrice().getAmount());
		}
		if (productDTO.getInventory() != null) {
			product.getInventory().setTotal(productDTO.getInventory().getTotal());
			product.getInventory().setAvailable(productDTO.getInventory().getAvailable());
			product.getInventory().setReserved(productDTO.getInventory().getReserved());
		}
		if (productDTO.getCategory() != null) {
			product.setCategory(productDTO.getCategory());
		}

		if (productDTO.getAttributes() != null) {
			product.setAttributes(new ArrayList<>(convertAttributes(productDTO.getAttributes())));
		}

		productRepository.save(product);
		System.out.println("ProductId: " + id);

		try {
			InventoryDTO inventoryDTO = inventoryProxy.getInventory(id);
			if (inventoryDTO == null) {
				System.err.println("Inventory not found for product ID: " + id);
				return productDTO;
			}

			System.out.println("inventoryDTO Before Update: " + inventoryDTO);

			inventoryDTO.setProductId(id); // Ensure ID is set
			inventoryDTO.setTotal(productDTO.getInventory().getTotal());
			inventoryDTO.setAvailable(productDTO.getInventory().getAvailable());
			inventoryDTO.setReserved(productDTO.getInventory().getReserved());

			inventoryProxy.updateInventory(id, inventoryDTO);

			System.out.println("Inventory updated successfully!");

		} catch (Exception e) {
			System.err.println("Failed to update inventory for product ID: " + id + " - " + e.getMessage());
		}

		return modelMapper.map(product, ProductDTO.class);
	}

	private List<ProductAttribute> convertAttributes(List<AttributeDTO> attributeDTOs) {
		return new ArrayList<>(attributeDTOs.stream().map(dto -> new ProductAttribute(dto.getName(), dto.getValue()))
				.collect(Collectors.toList())
		);
	}

}
