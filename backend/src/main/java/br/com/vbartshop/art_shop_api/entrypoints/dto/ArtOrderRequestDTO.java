package br.com.vbartshop.art_shop_api.entrypoints.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ArtOrderRequestDTO(
        @NotNull Long artworkId,
        @NotNull Long frameId,
        Long glassId,
        @NotNull Long sellerId,
        @NotNull Long customerId,
        @NotNull Double artworkHeightCm,
        @NotNull Double artworkWidthCm,
        @NotNull LocalDate deliveryDate,
        BigDecimal discountAmount
) {}
