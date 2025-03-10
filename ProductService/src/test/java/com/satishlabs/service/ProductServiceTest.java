package com.satishlabs.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import com.satishlabs.dto.InventoryDTO;
import com.satishlabs.dto.ProductDTO;
import com.satishlabs.entity.Inventory;
import com.satishlabs.entity.Price;
import com.satishlabs.entity.Product;
import com.satishlabs.proxy.InventoryProxy;
import com.satishlabs.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	@Mock
	private ProductRepository productRepository;

	@Mock
	private InventoryProxy inventoryProxy;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private ProductService productService;

	private Product product;
	private ProductDTO productDTO;

	@BeforeEach
	void setUp() {
	    product = new Product();
	    product.setId(1L);
	    product.setName("Test Product");
	    product.setCategory("Electronics");

	    Price price = new Price();
	    price.setAmount(BigDecimal.valueOf(100.0));
	    product.setPrice(price);

	    Inventory inventory = new Inventory();
	    inventory.setAvailable(5);
	    inventory.setTotal(10);  
	    product.setInventory(inventory);  

	    productDTO = new ProductDTO();
	    productDTO.setName("Test Product");
	}


	@Test
	void testGetProductsByCategory() {
		when(productRepository.findByCategoryAndInventoryAvailableGreaterThan("Electronics", 0))
				.thenReturn(Arrays.asList(product));
		when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);

		List<ProductDTO> result = productService.getProductsByCategory("Electronics", "price");

		assertFalse(result.isEmpty());
		assertEquals("Test Product", result.get(0).getName());
	}

	@Test
	void testGetProductsByCategory_ThrowsException() {
		when(productRepository.findByCategoryAndInventoryAvailableGreaterThan("Electronics", 0)).thenReturn(List.of());

		assertThrows(EntityNotFoundException.class, () -> productService.getProductsByCategory("Electronics", "price"));
	}

	@Test
	void testAddProduct() {
		when(productRepository.save(any(Product.class))).thenReturn(product);
		when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);
		when(inventoryProxy.addInventory(any(InventoryDTO.class))).thenReturn(ResponseEntity.ok().build());

		ProductDTO result = productService.addProduct(productDTO);

		assertNotNull(result);
		assertEquals("Test Product", result.getName());
	}

	@Test
	void testDeleteProduct() {
		doNothing().when(productRepository).deleteById(1L);

		assertDoesNotThrow(() -> productService.deleteProduct(1L));
	}

	@Test
	void testUpdateProduct() {
		when(productRepository.findById(1L)).thenReturn(Optional.of(product));
		when(productRepository.save(any(Product.class))).thenReturn(product);
		when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);
		when(inventoryProxy.getInventory(1L)).thenReturn(new InventoryDTO(1L, 10, 5, 5));
		doNothing().when(inventoryProxy).updateInventory(eq(1L), any(InventoryDTO.class));

		ProductDTO result = productService.updateProduct(1L, productDTO);

		assertNotNull(result);
		assertEquals("Test Product", result.getName());
	}

	@Test
	void testUpdateProduct_ThrowsException() {
		when(productRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> productService.updateProduct(1L, productDTO));
	}
}
