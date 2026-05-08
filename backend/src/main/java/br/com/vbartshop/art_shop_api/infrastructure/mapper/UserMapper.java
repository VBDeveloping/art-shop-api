package br.com.vbartshop.art_shop_api.infrastructure.mapper;


import br.com.vbartshop.art_shop_api.business.model.SystemUser;
import br.com.vbartshop.art_shop_api.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public SystemUser toModel(UserEntity entity) {
        if (entity == null) return null;

        return SystemUser.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .active(entity.isActive())
                .build();
    }

    public UserEntity toEntity(SystemUser model) {
        if (model == null) return null;

        return UserEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .email(model.getEmail())
                .password(model.getPassword())
                .role(model.getRole())
                .active(model.isActive())
                .build();
    }
}
