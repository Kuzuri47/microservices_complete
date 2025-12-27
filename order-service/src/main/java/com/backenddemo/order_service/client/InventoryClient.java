package com.backenddemo.order_service.client;

import com.backenddemo.order_service.dto.InventoryResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class InventoryClient {
    private RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:64318";

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
    }

    public List<InventoryResponse> getInventory(List<String> skuCodes) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(baseUrl + "/api/inventory")
                .queryParam("skuCode", skuCodes.toArray())
                .build()
                .toUri();

        ResponseEntity<List<InventoryResponse>> resp = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InventoryResponse>>() {}
        );

        return resp.getBody();
    }
}

