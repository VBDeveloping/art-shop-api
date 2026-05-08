package br.com.vbartshop.art_shop_api.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Glass {

    private Long id;
    private String type;
    private String description;
    private BigDecimal pricePerSquareMeter;
    private Double technicalGapCm;

    public BigDecimal calculateCost(Double artworkHeight, Double artworkWidth) {
        if (this.pricePerSquareMeter == null) return BigDecimal.ZERO;

        double h = (artworkHeight != null) ? artworkHeight : 0.0;
        double w = (artworkWidth != null) ? artworkWidth : 0.0;

        if (technicalGapCm != null) {
            h = Math.max(0, h - technicalGapCm);
            w = Math.max(0, w - technicalGapCm);
        }

        BigDecimal areaM2 = BigDecimal.valueOf(h)
                .multiply(BigDecimal.valueOf(w))
                .divide(BigDecimal.valueOf(10000));

        return pricePerSquareMeter.multiply(areaM2).setScale(2, java.math.RoundingMode.HALF_UP);
    }
}