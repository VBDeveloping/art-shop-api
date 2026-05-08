package br.com.vbartshop.art_shop_api.business.model.enums;

public enum UserRole {
    ADMIN,     // Acesso total (estoque, financeiro, usuários)
    SALES_USER // Acesso apenas para vendas e orçamentos
}
