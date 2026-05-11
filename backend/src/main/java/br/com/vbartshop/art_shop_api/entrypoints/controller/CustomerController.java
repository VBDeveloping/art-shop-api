package br.com.vbartshop.art_shop_api.entrypoints.controller;

import br.com.vbartshop.art_shop_api.business.model.Customer;
import br.com.vbartshop.art_shop_api.business.service.CustomerService;
import br.com.vbartshop.art_shop_api.entrypoints.dto.CustomerRequestDTO;
import br.com.vbartshop.art_shop_api.entrypoints.dto.CustomerResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream()
                .map(this::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CustomerRequestDTO dto) {
        Customer saved = service.save(toModel(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id,
                                                      @Valid @RequestBody CustomerRequestDTO dto) {
        Customer updated = service.update(id, toModel(dto));
        return ResponseEntity.ok(toResponse(updated));
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Void> toggleStatus(@PathVariable Long id) {
        service.toggleStatus(id);
        return ResponseEntity.noContent().build();
    }

    private Customer toModel(CustomerRequestDTO dto) {
        return Customer.builder()
                .name(dto.name()).email(dto.email())
                .phone(dto.phone()).cpf(dto.cpf())
                .address(dto.address()).build();
    }

    private CustomerResponseDTO toResponse(Customer c) {
        return new CustomerResponseDTO(c.getId(), c.getName(), c.getEmail(),
                c.getPhone(), c.getCpf(), c.getAddress(), c.isActive());
    }
}