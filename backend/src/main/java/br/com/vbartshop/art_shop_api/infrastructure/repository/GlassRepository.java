package br.com.vbartshop.art_shop_api.infrastructure.repository;

import br.com.vbartshop.art_shop_api.infrastructure.entity.GlassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlassRepository extends JpaRepository<GlassEntity, Long> {
}
