package br.com.vbartshop.art_shop_api.entrypoints.dto;

import br.com.vbartshop.art_shop_api.business.model.enums.PaymentStatus;
import br.com.vbartshop.art_shop_api.business.model.enums.PaymentType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDTO (
   Long id,
   Long orderId,
   BigDecimal amount,
   PaymentType type,
   PaymentStatus status,
   LocalDateTime paymentDate,
   String notes
) {}
