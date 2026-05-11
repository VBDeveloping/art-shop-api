package br.com.vbartshop.art_shop_api.entrypoints.dto;

public record CustomerResponseDTO(
        Long id,
        String name,
        String email,
        String phone,
        String cpf,
        String address,
        boolean active
) {}