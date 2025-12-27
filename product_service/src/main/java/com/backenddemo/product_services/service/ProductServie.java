package com.backenddemo.product_services.service;

import com.backenddemo.product_services.dto.ProductRequest;
import com.backenddemo.product_services.dto.ProductResposne;
import com.backenddemo.product_services.model.Product;
import com.backenddemo.product_services.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor   // for constructor injection(no need to do manually)
@Slf4j                     // for Logs
public class ProductServie {
    // Doing constructor injection
    private final ProductRepository productRepository;

//    public ProductServie(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    public void createProduct(ProductRequest productRequest) {
        // Step: 1 ->create product object
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .build();
        // Step 2 -> Now we will want to save it in database which we can do through repository
        productRepository.save(product);
        log.info("Product  " + product.getName() + " " + product.getName()  + " is saved");
    }


    // method to get All products
    public List<ProductResposne> getAllProducts() {
        // first read all prodcuts in database
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResposne mapToProductResponse(Product product) {
        return ProductResposne.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

}
