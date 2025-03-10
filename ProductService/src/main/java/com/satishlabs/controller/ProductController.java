package com.satishlabs.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.satishlabs.dto.ProductDTO;
import com.satishlabs.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@GetMapping("/category/{category}")
	public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category,
			@RequestParam(required = false) String sortBy) {

		List<ProductDTO> products = productService.getProductsByCategory(category, sortBy);
		return ResponseEntity.ok(products);
	}

	// http://localhost:8081/admin/products
	@PostMapping
	//@PreAuthorize("hasRole('ADMIN')") // Ensures only ADMIN can access these endpoints
	public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(productDTO));
	}

	// http://localhost:8081/admin/products/1
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

	// http://localhost:8081/admin/products/1
	@PutMapping("/{id}")
	//@PreAuthorize("hasRole('ADMIN')") // Ensures only ADMIN can access these endpoints
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
		return ResponseEntity.ok(productService.updateProduct(id, productDTO));
	}
}
