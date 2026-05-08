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
@Table(name = "frames")
public class Frame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    private String name;
    private String material;
    private String color;

    /**
     * Largura da face da moldura em centímetros.
     * Importante para o cálculo visual e para saber o quanto a moldura cobre a arte.
     */
    private Double faceWidthCm;
    private Double cuttingMarginCm;
    /**
     * Preço por metro linear (R$ / m).
     * Essencial para o cálculo do preço final baseado no perímetro.
     */
    private BigDecimal pricePerMeter;

    private Integer stockMeters; // Quantidade disponível em metros

    /**
     * Regra de Negócio: Calcula a metragem real necessária considerando os cortes.
     * A fórmula técnica para o perímetro externo (o que você realmente gasta) é:
     * Perímetro = [2 * (Altura + Largura)] + [8 * Largura da Moldura]
     */
    public Double calculateNeededMeters(Double artworkHeight, Double artworkWidth) {
        // O gasto real leva em conta que cada quina gasta 2x a largura da moldura
        Double realPerimeterCm = (2 * (artworkHeight + artworkWidth)) + (8 * (faceWidthCm != null ? faceWidthCm : 0));

        // Adicionamos uma pequena margem extra de segurança (ex: 2cm no total para serragem)
        if (cuttingMarginCm != null) {
            realPerimeterCm += cuttingMarginCm;
        }

        return realPerimeterCm / 100; // Converte para metros
    }
}