package br.com.vbartshop.art_shop_api.entrypoints.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ArtOrderResponseDTO(
        Long id,
        LocalDateTime orderDate,
        Double artworkHeightCm,
        Double artworkWidthCm,
        ArtworkResponseDTO artwork,
        FrameResponseDTO frame,
        GlassResponseDTO glass,
        UserResponseDTO seller,
        BigDecimal discountAmount,
        BigDecimal totalValue
) {}
