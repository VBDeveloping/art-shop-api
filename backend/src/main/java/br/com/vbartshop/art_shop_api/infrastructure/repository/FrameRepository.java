package br.com.vbartshop.art_shop_api.infrastructure.repository;

import br.com.vbartshop.art_shop_api.infrastructure.entity.FrameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FrameRepository extends JpaRepository<FrameEntity, Long> {
    Optional<FrameEntity> findByCode(String code);
}
