package com.satishlabs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satishlabs.dto.InventoryDTO;
import com.satishlabs.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
	private final InventoryService service;
	
	//http://localhost:7000/inventory/7
	@PatchMapping("/{productId}")
	public ResponseEntity<Void> updateInventory(@PathVariable Long productId, @RequestBody InventoryDTO inventoryDTO) {
		service.updateInventory(productId, inventoryDTO);
		return ResponseEntity.ok().build();
	}
	
	//http://localhost:7000/inventory/7
	@GetMapping("/{productId}")
	public ResponseEntity<InventoryDTO> getInventory(@PathVariable Long productId) {
		return ResponseEntity.ok(service.getInventory(productId));
	}
	
	//http://localhost:7000/inventory
	@PostMapping
	public ResponseEntity<Void> addInventory(@RequestBody InventoryDTO inventoryDTO) {
		service.addInventory(inventoryDTO);
		return ResponseEntity.ok().build();
	}

}