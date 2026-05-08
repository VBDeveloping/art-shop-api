package br.com.vbartshop.art_shop_api.business.model;

import br.com.vbartshop.art_shop_api.business.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemUser {
    private Long id;
    private String name;
    private String email;
    private String password; // No model, lidamos com a senha (criptografada)
    private UserRole role;   // ADMIN ou SALES_USER
    private boolean active;
}
