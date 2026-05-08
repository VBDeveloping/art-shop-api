package br.com.vbartshop.art_shop_api.entrypoints.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record FrameRequestDTO (
        @NotBlank String code,
        @NotBlank String name,
        String material,
        String color,
        Double faceWidthCm,
        Double cuttingMarginCm,
        @NotNull
        BigDecimal pricePerMeter,
        Integer stockMeters
){}
