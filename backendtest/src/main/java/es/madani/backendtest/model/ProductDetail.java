package es.madani.backendtest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Product detail")
public class ProductDetail {
    @Schema(description = "Product ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String id;

    @Schema(description = "Product name", example = "Shirt", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String name;

    @Schema(description = "Product price", example = "19.99", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Double price;

    @Schema(description = "Product availability", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Boolean availability;

    public ProductDetail() {
    }

    public ProductDetail(String id, String name, Double price, Boolean availability) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}