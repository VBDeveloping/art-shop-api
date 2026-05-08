package br.com.vbartshop.art_shop_api.entrypoints.dto;

import br.com.vbartshop.art_shop_api.business.model.enums.ArtSource;

import java.math.BigDecimal;

public record ArtworkResponseDTO (
        Long id,
        String title,
        String artist,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String imageUrl,
        ArtSource source
){}
