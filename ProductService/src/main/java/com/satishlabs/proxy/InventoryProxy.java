package com.satishlabs.proxy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.satishlabs.dto.InventoryDTO;



@HttpExchange(url = "/inventory")
public interface InventoryProxy {

    @PostExchange
    ResponseEntity<Void> addInventory(@RequestBody InventoryDTO inventoryDTO);

    @GetExchange("/{productId}")
    InventoryDTO getInventory(@PathVariable Long productId);

    @HttpExchange(method = "PATCH", url = "/{productId}")
    ResponseEntity<Void> updateInventory(@PathVariable Long productId, @RequestBody InventoryDTO inventoryDTO);
}


