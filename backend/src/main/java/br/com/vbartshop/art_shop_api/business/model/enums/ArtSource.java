package br.com.vbartshop.art_shop_api.business.model.enums;

/**
 * Origem da obra de arte para fins de cálculo e estoque.
 */
public enum ArtSource {
    STORE_INVENTORY, // Já está no acervo da VB Art Shop
    PRINT_SERVICE,   // Será impressa sob demanda
    CUSTOMER_OWNED   // Pertence ao cliente (apenas emolduramento)
}
