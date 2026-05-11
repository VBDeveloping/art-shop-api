package br.com.vbartshop.art_shop_api.business.service;

import br.com.vbartshop.art_shop_api.business.model.Customer;
import br.com.vbartshop.art_shop_api.infrastructure.entity.CustomerEntity;
import br.com.vbartshop.art_shop_api.infrastructure.mapper.CustomerMapper;
import br.com.vbartshop.art_shop_api.infrastructure.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return repository.findAll().stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Transactional
    public Customer save(Customer customer) {
        repository.findByCpf(customer.getCpf()).ifPresent(c -> {
            throw new RuntimeException("CPF já cadastrado.");
        });
        repository.findByEmail(customer.getEmail()).ifPresent(c -> {
            throw new RuntimeException("E-mail já cadastrado.");
        });
        customer.setActive(true);
        CustomerEntity entity = mapper.toEntity(customer);
        return mapper.toModel(repository.save(entity));
    }

    @Transactional
    public Customer update(Long id, Customer customer) {
        CustomerEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail());
        entity.setPhone(customer.getPhone());
        entity.setAddress(customer.getAddress());
        return mapper.toModel(repository.save(entity));
    }

    @Transactional
    public void toggleStatus(Long id) {
        CustomerEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        entity.setActive(!entity.isActive());
        repository.save(entity);
    }
}