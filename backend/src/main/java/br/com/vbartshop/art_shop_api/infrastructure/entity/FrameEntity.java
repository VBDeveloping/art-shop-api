package br.com.vbartshop.art_shop_api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_frames")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String name;

    private Double faceWidthCm;

    @Column(precision = 10, scale = 2)
    private BigDecimal pricePerMeter;

    private Integer stockMeters;
}