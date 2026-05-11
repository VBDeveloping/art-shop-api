package br.com.vbartshop.art_shop_api.infrastructure.mapper;


import br.com.vbartshop.art_shop_api.business.model.ArtOrder;
import br.com.vbartshop.art_shop_api.business.model.enums.OrderStatus;
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
    private final CustomerMapper customerMapper;


    public ArtOrder toModel(ArtOrderEntity entity){
        if(entity == null) return null;

        return ArtOrder.builder()
                .id(entity.getId())
                .orderDate(entity.getOrderDate())
                .deliveryDate(entity.getDeliveryDate())
                .status(entity.getStatus())
                .artworkHeightCm(entity.getArtworkHeightCm())
                .artworkWidthCm(entity.getArtworkWidthCm())
                .artwork(artworkMapper.toModel(entity.getArtwork()))
                .frame(frameMapper.toModel(entity.getFrame()))
                .glass(glassMapper.toModel(entity.getGlass()))
                .seller(userMapper.toModel(entity.getSeller()))
                .customer(customerMapper.toModel(entity.getCustomer()))
                .discountAmount(entity.getDiscountAmount())
                .build();
    }

    public ArtOrderEntity toEntity(ArtOrder model){
        if (model == null) return null;

        return ArtOrderEntity.builder()
                .orderDate(model.getOrderDate())
                .deliveryDate(model.getDeliveryDate())
                .status(model.getStatus())
                .artworkHeightCm(model.getArtworkHeightCm())
                .artworkWidthCm(model.getArtworkWidthCm())
                .artwork(artworkMapper.toEntity(model.getArtwork()))
                .frame(frameMapper.toEntity(model.getFrame()))
                .glass(glassMapper.toEntity(model.getGlass()))
                .seller(userMapper.toEntity(model.getSeller()))
                .customer(customerMapper.toEntity(model.getCustomer()))
                .discountAmount(model.getDiscountAmount())
                .totalValue(model.calculateTotalValue())
                .build();
    }
}
