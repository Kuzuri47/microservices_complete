package com.backenddemo.product_services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class ProductServicesApplicationTests {

	@Container
	static MongoDBContainer db = new MongoDBContainer("mongo:4.4.2");



	@Test
	void contextLoads() {
	}

}
