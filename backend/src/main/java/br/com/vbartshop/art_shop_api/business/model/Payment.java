package br.com.vbartshop.art_shop_api.business.model;

import br.com.vbartshop.art_shop_api.business.model.enums.PaymentStatus;
import br.com.vbartshop.art_shop_api.business.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private PaymentType type;
    private PaymentStatus status;
    private LocalDateTime paymentDate;
    private String notes;
}