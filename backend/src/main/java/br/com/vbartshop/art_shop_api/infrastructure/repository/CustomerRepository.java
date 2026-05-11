package br.com.vbartshop.art_shop_api.infrastructure.repository;

import br.com.vbartshop.art_shop_api.infrastructure.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByCpf(String cpf);
    Optional<CustomerEntity> findByEmail(String email);
}