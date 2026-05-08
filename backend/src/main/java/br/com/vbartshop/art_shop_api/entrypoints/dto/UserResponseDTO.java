package br.com.vbartshop.art_shop_api.entrypoints.dto;

import br.com.vbartshop.art_shop_api.business.model.enums.UserRole;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        UserRole role,
        boolean active
) {}
