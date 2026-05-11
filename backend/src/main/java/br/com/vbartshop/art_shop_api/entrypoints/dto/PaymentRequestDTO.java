package br.com.vbartshop.art_shop_api.entrypoints.dto;

import br.com.vbartshop.art_shop_api.business.model.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PaymentRequestDTO (
        @NotNull Long orderId,
        @NotNull BigDecimal amount,
        @NotNull PaymentType type,
        String notes
) {}
