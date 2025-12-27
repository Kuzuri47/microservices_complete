package com.backenddemo.inventoryservice.controller;

import com.backenddemo.inventoryservice.dto.InventoryResponse;
import com.backenddemo.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryservice;

//    @GetMapping("/{sku-code}")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        List<InventoryResponse> res = inventoryservice.isInStock(skuCode);
        res.forEach(element -> System.out.println(element));
        return res;
    }
}
