package br.com.vbartshop.art_shop_api.infrastructure.mapper;


import br.com.vbartshop.art_shop_api.business.model.ArtOrder;
import br.com.vbartshop.art_shop_api.infrastructure.entity.ArtOrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArtOrderMapper {
    private final FrameMapper frameMapper;
    private final ArtworkMapper artworkMapper;
    private final GlassMapper glassMapper;
    private final UserMapper userMapper;



    public ArtOrder toModel(ArtOrderEntity entity){
        if(entity == null) return null;

        return ArtOrder.builder()
                .id(entity.getId())
                .orderDate(entity.getOrderDate())
                .artworkHeightCm(entity.getArtworkHeightCm())
                .artworkWidthCm(entity.getArtworkWidthCm())
                .artwork(artworkMapper.toModel(entity.getArtwork()))
                .frame(frameMapper.toModel(entity.getFrame()))
                .glass(glassMapper.toModel(entity.getGlass()))
                .seller(userMapper.toModel(entity.getSeller()))
                .discountAmount(entity.getDiscountAmount())
                .build();
    }

    public ArtOrderEntity toEntity(ArtOrder model){
        if (model == null) return null;

        return ArtOrderEntity.builder()
                .orderDate(model.getOrderDate())
                .artworkHeightCm(model.getArtworkHeightCm())
                .artworkWidthCm(model.getArtworkWidthCm())
                .artwork(artworkMapper.toEntity(model.getArtwork()))
                .frame(frameMapper.toEntity(model.getFrame()))
                .glass(glassMapper.toEntity(model.getGlass()))
                .seller(userMapper.toEntity(model.getSeller()))
                .discountAmount(model.getDiscountAmount())
                .totalValue(model.calculateTotalValue())
                .build();
    }
}
