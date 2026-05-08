package br.com.vbartshop.art_shop_api.entrypoints.dto;

import java.math.BigDecimal;

public record GlassResponseDTO(
        Long id,
        String type,
        String description,
        BigDecimal pricePerSquareMeter
) {}
