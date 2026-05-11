package br.com.vbartshop.art_shop_api.business.service;

import br.com.vbartshop.art_shop_api.business.model.Payment;
import br.com.vbartshop.art_shop_api.business.model.enums.PaymentStatus;
import br.com.vbartshop.art_shop_api.infrastructure.mapper.PaymentMapper;
import br.com.vbartshop.art_shop_api.infrastructure.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    @Transactional(readOnly = true)
    public List<Payment> findByOrderId(Long orderId) {
        return repository.findByOrderId(orderId).stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public Payment create(Payment payment) {
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaymentDate(LocalDateTime.now());
        return mapper.toModel(repository.save(mapper.toEntity(payment)));
    }

    @Transactional
    public Payment cancel(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado."));
        entity.setStatus(PaymentStatus.CANCELLED);
        return mapper.toModel(repository.save(entity));
    }
}