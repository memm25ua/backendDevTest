package es.madani.backendtest.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import es.madani.backendtest.model.ProductDetail;

@Service
public class ExternalProductApiService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ExternalProductApiService(@Value("${external.api.base-url:http://localhost:3001}") String baseUrl) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
    }

    public List<String> getSimilarProductIds(String productId) {
        String url = String.format("%s/product/%s/similarids", baseUrl, productId);
        try {
            ResponseEntity<String[]> response = restTemplate.getForEntity(url, String[].class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        } catch (RestClientException e) {
            // log error if needed
        }
        return Collections.emptyList();
    }

    public Optional<ProductDetail> getProductDetail(String productId) {
        String url = String.format("%s/product/%s", baseUrl, productId);
        try {
            ResponseEntity<ProductDetail> response = restTemplate.getForEntity(url, ProductDetail.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Optional.of(response.getBody());
            }
        } catch (RestClientException e) {
            // log error if needed
        }
        return Optional.empty();
    }
}