package br.com.vbartshop.art_shop_api.entrypoints.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record GlassRequestDTO(
        @NotBlank String type,
        String description,
        @NotNull BigDecimal pricePerSquareMeter,
        Double technicalGapCm
) {}
