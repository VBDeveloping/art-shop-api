package br.com.vbartshop.art_shop_api.infrastructure.mapper;

import br.com.vbartshop.art_shop_api.business.model.Glass;
import br.com.vbartshop.art_shop_api.infrastructure.entity.GlassEntity;
import org.springframework.stereotype.Component;

@Component
public class GlassMapper {

    public Glass toModel(GlassEntity entity){
        if(entity == null) return null;

        return Glass.builder()
                .id(entity.getId())
                .type(entity.getType())
                .description(entity.getDescription())
                .pricePerSquareMeter(entity.getPricePerSquareMeter())
                .technicalGapCm(entity.getTechnicalGapCm())
                .build();
    }

    public GlassEntity toEntity(Glass model){
        if(model == null) return null;

        return GlassEntity.builder()
                .id(model.getId())
                .type(model.getType())
                .description(model.getDescription())
                .pricePerSquareMeter(model.getPricePerSquareMeter())
                .technicalGapCm(model.getTechnicalGapCm())
                .build();
    }
}
