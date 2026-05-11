package br.com.vbartshop.art_shop_api.infrastructure.mapper;

import br.com.vbartshop.art_shop_api.business.model.Customer;
import br.com.vbartshop.art_shop_api.infrastructure.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toModel(CustomerEntity entity) {
        if (entity == null) return null;
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .cpf(entity.getCpf())
                .address(entity.getAddress())
                .active(entity.isActive())
                .build();
    }

    public CustomerEntity toEntity(Customer model) {
        if (model == null) return null;
        return CustomerEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .email(model.getEmail())
                .phone(model.getPhone())
                .cpf(model.getCpf())
                .address(model.getAddress())
                .active(model.isActive())
                .build();
    }
}