package br.com.vbartshop.art_shop_api.infrastructure.entity;

import br.com.vbartshop.art_shop_api.business.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    private LocalDate deliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    // Dimensões
    private Double artworkHeightCm;
    private Double artworkWidthCm;

    // Relacionamentos (Chaves Estrangeiras no Postgres)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    private ArtworkEntity artwork;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frame_id")
    private FrameEntity frame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glass_id")
    private GlassEntity glass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private UserEntity seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Column(precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalValue; // Valor final persistido
}