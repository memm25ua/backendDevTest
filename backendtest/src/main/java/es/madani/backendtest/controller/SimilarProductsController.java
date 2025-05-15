package es.madani.backendtest.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.madani.backendtest.model.SimilarProducts;
import es.madani.backendtest.service.ExternalProductApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/product")
public class SimilarProductsController {

    private final ExternalProductApiService externalProductApiService;

    public SimilarProductsController(ExternalProductApiService externalProductApiService) {
        this.externalProductApiService = externalProductApiService;
    }

    @Operation(summary = "Similar products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product Not found")
    })
    @GetMapping("/{productId}/similar")
    public ResponseEntity<?> getSimilarProducts(@PathVariable String productId) {
        Optional<SimilarProducts> result = externalProductApiService.getSimilarProducts(productId);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not found");
        }
        return ResponseEntity.ok(result.get());
    }
}