package br.com.vbartshop.art_shop_api.entrypoints.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequestDTO(
        @NotBlank String name,
        @Email String email,
        String phone,
        @NotBlank String cpf,
        String address
) {}