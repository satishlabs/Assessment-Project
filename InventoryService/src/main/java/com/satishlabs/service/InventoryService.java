package com.satishlabs.service;

import org.springframework.stereotype.Service;

import com.satishlabs.dto.InventoryDTO;
import com.satishlabs.entity.Inventory;
import com.satishlabs.repository.InventoryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
	private final InventoryRepository inventoryRepository;

	public InventoryDTO getInventory(Long productId) {
		Inventory inventory = inventoryRepository.findByProductId(productId)
				.orElseThrow(() -> new EntityNotFoundException("Inventory not found for product " + productId));

		return new InventoryDTO(productId, inventory.getTotal(), inventory.getAvailable(), inventory.getReserved());
	}

	public void updateInventory(Long productId, InventoryDTO inventoryDTO) {
		Inventory inventory = inventoryRepository.findByProductId(productId)
				.orElseThrow(() -> new RuntimeException("Inventory not found for product ID: " + productId));
		inventory.setAvailable(inventoryDTO.getAvailable());
		inventory.setReserved(inventoryDTO.getReserved());
		inventory.setTotal(inventoryDTO.getTotal());
		inventoryRepository.save(inventory);
	}

	public void addInventory(InventoryDTO inventoryDTO) {
		if (inventoryRepository.findByProductId(inventoryDTO.getProductId()).isPresent()) {
			throw new IllegalStateException(
					"Inventory for product ID " + inventoryDTO.getProductId() + " already exists.");
		}
		Inventory inventory = new Inventory(inventoryDTO.getProductId(), inventoryDTO.getTotal(),
				inventoryDTO.getAvailable(), inventoryDTO.getReserved());
		inventoryRepository.save(inventory);
	}

}
