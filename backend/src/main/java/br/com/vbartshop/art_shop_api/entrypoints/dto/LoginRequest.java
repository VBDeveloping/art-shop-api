package br.com.vbartshop.art_shop_api.entrypoints.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
