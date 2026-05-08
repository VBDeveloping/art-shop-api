package br.com.vbartshop.art_shop_api.entrypoints.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ArtOrderRequestDTO(
        @NotNull Long artworkId,
        @NotNull Long frameId,
        Long glassId,
        @NotNull Long sellerId,
        @NotNull Double artworkHeightCm,
        @NotNull Double artworkWidthCm,
        BigDecimal discountAmount
) {}
