package br.com.vbartshop.art_shop_api.infrastructure.mapper;

import br.com.vbartshop.art_shop_api.business.model.Frame;
import br.com.vbartshop.art_shop_api.infrastructure.entity.FrameEntity;
import org.springframework.stereotype.Component;

@Component
public class FrameMapper {

    // Entity -> Model
    public Frame toModel(FrameEntity entity){
        if(entity == null) return null;

        return Frame.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .material(entity.getMaterial())
                .color(entity.getColor())
                .faceWidthCm(entity.getFaceWidthCm())
                .cuttingMarginCm(entity.getCuttingMarginCm())
                .pricePerMeter(entity.getPricePerMeter())
                .stockMeters(entity.getStockMeters())
                .build();
    }

    // Model -> Entity
    public FrameEntity toEntity(Frame model){
        if(model == null) return null;

        return FrameEntity.builder()
                .id(model.getId())
                .code(model.getCode())
                .name(model.getName())
                .material(model.getMaterial())
                .color(model.getColor())
                .faceWidthCm(model.getFaceWidthCm())
                .cuttingMarginCm(model.getCuttingMarginCm())
                .pricePerMeter(model.getPricePerMeter())
                .stockMeters(model.getStockMeters())
                .build();
    }
}
