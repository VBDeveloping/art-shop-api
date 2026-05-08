package br.com.vbartshop.art_shop_api.entrypoints.dto;

import java.math.BigDecimal;

public record FrameResponseDTO (
        Long id,
        String code,
        String name,
        String material,
        String color,
        Double faceWidthCm,
        BigDecimal pricePerMeter,
        Integer stockMeters
){}
