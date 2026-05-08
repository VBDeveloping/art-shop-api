package br.com.vbartshop.art_shop_api.business.model;

import br.com.vbartshop.art_shop_api.business.model.enums.ArtSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artwork {

    private Long id;
    private String title;
    private String artist;
    private String description;
    private BigDecimal price; // Preço de venda se for da loja
    private Integer stockQuantity;
    private String imageUrl;
    private ArtSource source; // Loja, Cliente ou Impressão

    /**
     * Regra de Negócio: Verifica se a arte está disponível para venda.
     * Se for do cliente, sempre está "disponível" para emoldurar.
     * Se for da loja, precisa ter estoque.
     */
    public boolean isAvailableForOrder() {
        if (ArtSource.CUSTOMER_OWNED.equals(this.source)) {
            return true;
        }
        return stockQuantity != null && stockQuantity > 0;
    }

    /**
     * Regra de Negócio: Se a arte for do cliente, o preço base para o cálculo é zero.
     */
    public BigDecimal getBasePriceForCalculation() {
        if (ArtSource.CUSTOMER_OWNED.equals(this.source)) {
            return BigDecimal.ZERO;
        }
        return this.price != null ? this.price : BigDecimal.ZERO;
    }
}
