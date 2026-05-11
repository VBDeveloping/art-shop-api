package br.com.vbartshop.art_shop_api.entrypoints.controller;

import br.com.vbartshop.art_shop_api.business.model.Payment;
import br.com.vbartshop.art_shop_api.business.service.PaymentService;
import br.com.vbartshop.art_shop_api.entrypoints.dto.PaymentRequestDTO;
import br.com.vbartshop.art_shop_api.entrypoints.dto.PaymentResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentResponseDTO>> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(service.findByOrderId(orderId).stream()
                .map(this::toResponse).toList());
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> create(@Valid @RequestBody PaymentRequestDTO dto) {
        Payment saved = service.create(toModel(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<PaymentResponseDTO> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(service.cancel(id)));
    }

    private Payment toModel(PaymentRequestDTO dto) {
        return Payment.builder()
                .orderId(dto.orderId())
                .amount(dto.amount())
                .type(dto.type())
                .notes(dto.notes())
                .build();
    }

    private PaymentResponseDTO toResponse(Payment p) {
        return new PaymentResponseDTO(p.getId(), p.getOrderId(), p.getAmount(),
                p.getType(), p.getStatus(), p.getPaymentDate(), p.getNotes());
    }
}