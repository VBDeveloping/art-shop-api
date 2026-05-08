package br.com.vbartshop.art_shop_api.entrypoints.dto;

import br.com.vbartshop.art_shop_api.business.model.enums.ArtSource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ArtworkRequestDTO (
        @NotBlank String title,
        String artist,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String imageUrl,
        @NotNull ArtSource source
){}
