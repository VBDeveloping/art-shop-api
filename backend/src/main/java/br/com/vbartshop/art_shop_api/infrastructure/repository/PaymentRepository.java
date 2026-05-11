package br.com.vbartshop.art_shop_api.infrastructure.repository;

import br.com.vbartshop.art_shop_api.infrastructure.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findByOrderId(Long orderId);
}