package br.com.vbartshop.art_shop_api.infrastructure.mapper;

import br.com.vbartshop.art_shop_api.business.model.Artwork;
import br.com.vbartshop.art_shop_api.infrastructure.entity.ArtworkEntity;
import org.springframework.stereotype.Component;

@Component
public class ArtworkMapper {

    // Entity -> Model
    public Artwork toModel(ArtworkEntity entity){
        if(entity == null) return null;

        return Artwork.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .artist(entity.getArtist())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stockQuantity(entity.getStockQuantity())
                .imageUrl(entity.getImageUrl())
                .source(entity.getSource())
                .build();
    }
    // Model -> Entity
    public ArtworkEntity toEntity(Artwork model){
        if(model == null) return null;

        return ArtworkEntity.builder()
                .id(model.getId())
                .title(model.getTitle())
                .artist(model.getArtist())
                .description(model.getDescription())
                .price(model.getPrice())
                .stockQuantity(model.getStockQuantity())
                .imageUrl(model.getImageUrl())
                .source(model.getSource())
                .build();
    }
}
