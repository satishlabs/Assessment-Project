/*package com.satishlabs.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satishlabs.dto.ProductDTO;
import com.satishlabs.service.ProductService;

class ProductControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	private ProductDTO productDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

		productDTO = new ProductDTO();
		productDTO.setName("Test Product");
		productDTO.setCategory("Electronics");
	}

	@Test
	void testGetProductsByCategory() throws Exception {
		List<ProductDTO> products = Arrays.asList(productDTO);
		when(productService.getProductsByCategory("Electronics", null)).thenReturn(products);

		mockMvc.perform(get("/admin/products/category/Electronics")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(1)).andExpect(jsonPath("$[0].name").value("Test Product"));

		verify(productService, times(1)).getProductsByCategory("Electronics", null);
	}

	@Test
	void testAddProduct() throws Exception {
		when(productService.addProduct(any(ProductDTO.class))).thenReturn(productDTO);

		mockMvc.perform(post("/admin/products").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(productDTO))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Test Product"));

		verify(productService, times(1)).addProduct(any(ProductDTO.class));
	}

	@Test
	void testDeleteProduct() throws Exception {
		doNothing().when(productService).deleteProduct(1L);

		mockMvc.perform(delete("/admin/products/1")).andExpect(status().isNoContent());

		verify(productService, times(1)).deleteProduct(1L);
	}

	@Test
	void testUpdateProduct() throws Exception {
		when(productService.updateProduct(eq(1L), any(ProductDTO.class))).thenReturn(productDTO);

		mockMvc.perform(put("/admin/products/1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(productDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Test Product"));

		verify(productService, times(1)).updateProduct(eq(1L), any(ProductDTO.class));
	}
}
*/