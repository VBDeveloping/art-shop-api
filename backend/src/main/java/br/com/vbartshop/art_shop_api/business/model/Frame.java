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

public class Frame {

    private Long id;
    private String code;
    private String name;
    private String material;
    private String color;
    private Double faceWidthCm;
    private Double cuttingMarginCm;
    private BigDecimal pricePerMeter;
    private Integer stockMeters;

    public Double calculateNeededMeters(Double artworkHeight, Double artworkWidth) {
        Double realPerimeterCm = (2 * (artworkHeight + artworkWidth)) + (8 * (faceWidthCm != null ? faceWidthCm : 0));
        if (cuttingMarginCm != null) {
            realPerimeterCm += cuttingMarginCm;
        }
        return realPerimeterCm / 100;
    }
}