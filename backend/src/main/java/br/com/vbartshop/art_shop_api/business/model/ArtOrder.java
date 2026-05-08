package br.com.vbartshop.art_shop_api.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtOrder {

    private Long id;
    private LocalDateTime orderDate;
    private SystemUser seller; // Quem realizou a venda

    // Dimensões da obra de arte (internas)
    private Double artworkHeightCm;
    private Double artworkWidthCm;

    // Componentes selecionados
    private Artwork artwork;
    private Frame frame;
    private Glass glass;

    // Custos Adicionais     // Mão de obra/Montagem
    private BigDecimal discountAmount; // Desconto pontual

    /**
     * Regra de Negócio Central: Calcula o valor total do orçamento.
     * Este método orquestra os cálculos individuais de cada componente.
     */
    public BigDecimal calculateTotalValue() {
        BigDecimal total = BigDecimal.ZERO;

        // Valor da Arte (se for da loja ou impressão)
        if (artwork != null) {
            total = total.add(artwork.getBasePriceForCalculation());
        }

        // Valor da Moldura (usando a regra de perímetro real com cortes)
        if (frame != null && frame.getPricePerMeter() == null) {
            Double neededMeters = frame.calculateNeededMeters(artworkHeightCm, artworkWidthCm);
            BigDecimal framePrice = frame.getPricePerMeter().multiply(BigDecimal.valueOf(neededMeters));
            total = total.add(framePrice);
        }

        // Valor do Vidro (área em m2)
        if (glass != null) {
            BigDecimal glassPrice = glass.calculateCost(artworkHeightCm, artworkWidthCm);
            total = total.add(glassPrice);
        }

        // Aplica Desconto
        if (discountAmount != null) {
            total = total.subtract(discountAmount);
        }

        return total.max(BigDecimal.ZERO); // Garante que o total não seja negativo
    }

    public double calculatePerimeterInMeters() {
        return (this.artworkHeightCm + this.artworkWidthCm) * 2 / 100.0;
    }
}
