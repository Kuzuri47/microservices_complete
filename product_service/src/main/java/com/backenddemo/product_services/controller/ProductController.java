package com.backenddemo.product_services.controller;

import com.backenddemo.product_services.dto.ProductRequest;
import com.backenddemo.product_services.dto.ProductResposne;
import com.backenddemo.product_services.service.ProductServie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller -> Service(dependecy repository) -> saves to database
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor        // for constructor injection(no need to do manually)
public class ProductController {
    private final ProductServie productServie;

//    public ProductController(ProductServie productServie) {
//        this.productServie = productServie;
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productServie.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResposne> getAllProducts() {
        return productServie.getAllProducts();
    }
}
