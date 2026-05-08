package br.com.vbartshop.art_shop_api.business.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "glasses")
public class Glass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "glass_type")
    private String type;
    private String description;

    /**
     * Preço por metro quadrado (R$ / m²).
     */
    @Column(name = "price_per_square_meter", nullable = false)
    private BigDecimal pricePerSquareMeter = BigDecimal.ZERO;

    /**
     * Folga técnica em centímetros.
     * Na montagem, o vidro costuma ser cortado 0.2cm (2mm) menor que o
     * espaço da moldura para facilitar o encaixe.
     */
    private Double technicalGapCm;

    /**
     * Regra de Negócio: Calcula o custo do vidro baseado na área da arte.
     * @param artworkHeight Altura da imagem
     * @param artworkWidth Largura da imagem
     * @return O custo total do vidro para estas dimensões.
     */
    public BigDecimal calculateCost(Double artworkHeight, Double artworkWidth) {
        // Se não há preço definido, o custo é zero
        if (this.pricePerSquareMeter == null) {
            return BigDecimal.ZERO;
        }

        // Garantia de valores para dimensões
        double h = (artworkHeight != null) ? artworkHeight : 0.0;
        double w = (artworkWidth != null) ? artworkWidth : 0.0;

        // Aplica a folga técnica
        if (technicalGapCm != null) {
            h = Math.max(0, h - technicalGapCm);
            w = Math.max(0, w - technicalGapCm);
        }

        // Área em metros quadrados (cm * cm / 10000)
        BigDecimal areaM2 = BigDecimal.valueOf(h)
                .multiply(BigDecimal.valueOf(w))
                .divide(BigDecimal.valueOf(10000));

        // Se a área for muito pequena, algumas lojas cobram um mínimo (ex: 0.25 m²)
        // Por agora, vamos usar a área real
        return pricePerSquareMeter.multiply(areaM2).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
