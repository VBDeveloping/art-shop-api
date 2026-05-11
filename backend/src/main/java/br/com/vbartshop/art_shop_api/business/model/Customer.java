package br.com.vbartshop.art_shop_api.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String cpf;
    private String address;
    private boolean active;
}