package br.com.vbartshop.art_shop_api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_glasses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(precision = 10, scale = 2)
    private BigDecimal pricePerSquareMeter;
}