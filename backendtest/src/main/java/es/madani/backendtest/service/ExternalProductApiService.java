package es.madani.backendtest.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import es.madani.backendtest.model.ProductDetail;
import es.madani.backendtest.model.SimilarProducts;

@Service
public class ExternalProductApiService {

    private static final Logger logger = LoggerFactory.getLogger(ExternalProductApiService.class);

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ExternalProductApiService(@Value("${external.api.base-url:http://localhost:3001}") String baseUrl) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000); // 2 segundos
        factory.setReadTimeout(2000); // 2 segundos
        this.restTemplate = new RestTemplate(factory);
        this.baseUrl = baseUrl;
    }

    private List<String> getSimilarProductIds(String productId) {
        String url = String.format("%s/product/%s/similarids", baseUrl, productId);
        try {
            ResponseEntity<String[]> response = restTemplate.getForEntity(url, String[].class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        } catch (RestClientException e) {
            logger.warn("Error fetching similar product ids for {}: {}", productId, e.getMessage());
        }
        return Collections.emptyList();
    }

    private Optional<ProductDetail> getProductDetail(String productId) {
        String url = String.format("%s/product/%s", baseUrl, productId);
        try {
            ResponseEntity<ProductDetail> response = restTemplate.getForEntity(url, ProductDetail.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Optional.of(response.getBody());
            }
        } catch (RestClientException e) {
            logger.warn("Error fetching product detail for {}: {}", productId, e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<SimilarProducts> getSimilarProducts(String productId) {
        Optional<ProductDetail> mainProduct = getProductDetail(productId);
        if (mainProduct.isEmpty()) {
            return Optional.empty();
        }
        List<String> similarIds = getSimilarProductIds(productId);
        List<ProductDetail> similarProducts = similarIds.stream()
                .map(this::getProductDetail)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return Optional.of(new SimilarProducts(similarProducts));
    }
}