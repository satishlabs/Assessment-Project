package com.satishlabs.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.satishlabs.dto.InventoryDTO;
import com.satishlabs.entity.Inventory;
import com.satishlabs.repository.InventoryRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Inventory inventory;
    private InventoryDTO inventoryDTO;

    @BeforeEach
    void setUp() {
        inventory = new Inventory(1L, 100, 50, 50);
        inventoryDTO = new InventoryDTO(1L, 100, 50, 50);
    }

   
    @Test
    void testGetInventory_Success() {
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));

        InventoryDTO result = inventoryService.getInventory(1L);

        assertNotNull(result);
        assertEquals(100, result.getTotal());
        assertEquals(50, result.getAvailable());
        assertEquals(50, result.getReserved());

        verify(inventoryRepository, times(1)).findByProductId(1L);
    }

   
    @Test
    void testGetInventory_NotFound() {
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> inventoryService.getInventory(1L));

        verify(inventoryRepository, times(1)).findByProductId(1L);
    }

 
    @Test
    void testUpdateInventory_Success() {
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));

        inventoryService.updateInventory(1L, inventoryDTO);

        assertEquals(100, inventory.getTotal());
        assertEquals(50, inventory.getAvailable());
        assertEquals(50, inventory.getReserved());

        verify(inventoryRepository, times(1)).findByProductId(1L);
        verify(inventoryRepository, times(1)).save(inventory);
    }


    @Test
    void testUpdateInventory_NotFound() {
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> inventoryService.updateInventory(1L, inventoryDTO));

        verify(inventoryRepository, times(1)).findByProductId(1L);
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }


    @Test
    void testAddInventory_Success() {
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.empty());

        inventoryService.addInventory(inventoryDTO);

        verify(inventoryRepository, times(1)).findByProductId(1L);
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }


    @Test
    void testAddInventory_AlreadyExists() {
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));

        assertThrows(IllegalStateException.class, () -> inventoryService.addInventory(inventoryDTO));

        verify(inventoryRepository, times(1)).findByProductId(1L);
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }
}
