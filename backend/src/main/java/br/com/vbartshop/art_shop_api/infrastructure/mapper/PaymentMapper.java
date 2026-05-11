package br.com.vbartshop.art_shop_api.infrastructure.mapper;

import br.com.vbartshop.art_shop_api.business.model.Payment;
import br.com.vbartshop.art_shop_api.infrastructure.entity.ArtOrderEntity;
import br.com.vbartshop.art_shop_api.infrastructure.entity.PaymentEntity;
import br.com.vbartshop.art_shop_api.infrastructure.repository.ArtOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    private final ArtOrderRepository orderRepository;

    public Payment toModel(PaymentEntity entity) {
        if (entity == null) return null;
        return Payment.builder()
                .id(entity.getId())
                .orderId(entity.getOrder().getId())
                .amount(entity.getAmount())
                .type(entity.getType())
                .status(entity.getStatus())
                .paymentDate(entity.getPaymentDate())
                .notes(entity.getNotes())
                .build();
    }

    public PaymentEntity toEntity(Payment model) {
        if (model == null) return null;

        ArtOrderEntity order = orderRepository.findById(model.getOrderId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));

        return PaymentEntity.builder()
                .id(model.getId())
                .order(order)
                .amount(model.getAmount())
                .type(model.getType())
                .status(model.getStatus())
                .paymentDate(model.getPaymentDate())
                .notes(model.getNotes())
                .build();
    }
}