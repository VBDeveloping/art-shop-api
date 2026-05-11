package br.com.vbartshop.art_shop_api.entrypoints.dto;

import br.com.vbartshop.art_shop_api.business.model.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ArtOrderResponseDTO(
        Long id,
        LocalDateTime orderDate,
        LocalDate deliveryDate,
        OrderStatus status,
        Double artworkHeightCm,
        Double artworkWidthCm,
        ArtworkResponseDTO artwork,
        FrameResponseDTO frame,
        GlassResponseDTO glass,
        UserResponseDTO seller,
        CustomerResponseDTO customer,
        BigDecimal discountAmount,
        BigDecimal totalValue
) {}
