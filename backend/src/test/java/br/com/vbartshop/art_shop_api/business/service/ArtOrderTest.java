package br.com.vbartshop.art_shop_api.business.model;

import br.com.vbartshop.art_shop_api.business.model.enums.ArtSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ArtOrder - Regras de negócio")
class ArtOrderTest {

    @Test
    @DisplayName("deve calcular total com obra, moldura e vidro")
    void calculateTotalValue_deveCalcularCorretamente() {
        Artwork artwork = Artwork.builder()
                .price(new BigDecimal("350.00"))
                .source(ArtSource.STORE_INVENTORY)
                .build();

        Frame frame = Frame.builder()
                .pricePerMeter(new BigDecimal("25.90"))
                .faceWidthCm(3.5)
                .cuttingMarginCm(0.2)
                .build();

        Glass glass = Glass.builder()
                .pricePerSquareMeter(new BigDecimal("45.00"))
                .build();

        ArtOrder order = ArtOrder.builder()
                .artwork(artwork)
                .frame(frame)
                .glass(glass)
                .artworkHeightCm(40.0)
                .artworkWidthCm(30.0)
                .build();

        BigDecimal total = order.calculateTotalValue();

        assertThat(total).isGreaterThan(BigDecimal.ZERO);
        assertThat(total).isGreaterThan(new BigDecimal("350.00"));
    }

    @Test
    @DisplayName("deve retornar zero de arte quando for CUSTOMER_OWNED")
    void calculateTotalValue_deveIgnorarPrecoArte_quandoCustomerOwned() {
        Artwork artwork = Artwork.builder()
                .price(new BigDecimal("500.00"))
                .source(ArtSource.CUSTOMER_OWNED)
                .build();

        ArtOrder order = ArtOrder.builder()
                .artwork(artwork)
                .artworkHeightCm(30.0)
                .artworkWidthCm(20.0)
                .build();

        BigDecimal total = order.calculateTotalValue();

        assertThat(total).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("deve aplicar desconto corretamente")
    void calculateTotalValue_deveAplicarDesconto() {
        Artwork artwork = Artwork.builder()
                .price(new BigDecimal("350.00"))
                .source(ArtSource.STORE_INVENTORY)
                .build();

        ArtOrder order = ArtOrder.builder()
                .artwork(artwork)
                .artworkHeightCm(30.0)
                .artworkWidthCm(20.0)
                .discountAmount(new BigDecimal("50.00"))
                .build();

        BigDecimal total = order.calculateTotalValue();

        assertThat(total).isEqualByComparingTo(new BigDecimal("300.00"));
    }

    @Test
    @DisplayName("total nunca deve ser negativo")
    void calculateTotalValue_nuncaDeveSerNegativo() {
        Artwork artwork = Artwork.builder()
                .price(new BigDecimal("100.00"))
                .source(ArtSource.STORE_INVENTORY)
                .build();

        ArtOrder order = ArtOrder.builder()
                .artwork(artwork)
                .artworkHeightCm(10.0)
                .artworkWidthCm(10.0)
                .discountAmount(new BigDecimal("999.00"))
                .build();

        BigDecimal total = order.calculateTotalValue();

        assertThat(total).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("deve calcular perímetro em metros corretamente")
    void calculatePerimeterInMeters_deveCalcularCorretamente() {
        ArtOrder order = ArtOrder.builder()
                .artworkHeightCm(50.0)
                .artworkWidthCm(40.0)
                .build();

        double perimeter = order.calculatePerimeterInMeters();

        assertThat(perimeter).isEqualTo(1.8);
    }
}