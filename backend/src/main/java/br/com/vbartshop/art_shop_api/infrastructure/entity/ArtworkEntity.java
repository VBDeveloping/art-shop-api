package br.com.vbartshop.art_shop_api.infrastructure.entity;

import br.com.vbartshop.art_shop_api.business.model.enums.ArtSource;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_artworks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtworkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String artist;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    private Integer stockQuantity;

    private String imageUrl;

    @Enumerated(EnumType.STRING) // Salva o texto (ex: "STORE_INVENTORY") no banco
    @Column(nullable = false)
    private ArtSource source;
}