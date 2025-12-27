package com.backenddemo.product_services.repository;

import com.backenddemo.product_services.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

// String because id is String in product class
public interface ProductRepository extends MongoRepository<Product, String> {
}
