package es.madani.backendtest.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Schema(description = "List of similar products to a given one ordered by similarity")
public class SimilarProducts {
    @Schema(description = "List of similar products", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Valid
    private List<ProductDetail> products;

    public SimilarProducts() {
    }

    public SimilarProducts(List<ProductDetail> products) {
        this.products = products;
    }

    public List<ProductDetail> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDetail> products) {
        this.products = products;
    }
}