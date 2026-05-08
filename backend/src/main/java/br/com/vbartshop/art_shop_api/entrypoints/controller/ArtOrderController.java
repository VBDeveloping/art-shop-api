package br.com.vbartshop.art_shop_api.entrypoints.controller;

import br.com.vbartshop.art_shop_api.business.model.*;
import br.com.vbartshop.art_shop_api.business.service.ArtOrderService;
import br.com.vbartshop.art_shop_api.entrypoints.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/art-orders")
@RequiredArgsConstructor
public class ArtOrderController {

    private final ArtOrderService service;

    @PostMapping
    public ResponseEntity<ArtOrderResponseDTO> placeOrder(@Valid @RequestBody ArtOrderRequestDTO dto) {
        ArtOrder order = toModel(dto);
        ArtOrder saved = service.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<ArtOrderResponseDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream()
                .map(this::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtOrderResponseDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private ArtOrder toModel(ArtOrderRequestDTO dto) {
        return ArtOrder.builder()
                .artwork(Artwork.builder().id(dto.artworkId()).build())
                .frame(Frame.builder().id(dto.frameId()).build())
                .glass(dto.glassId() != null ? Glass.builder().id(dto.glassId()).build() : null)
                .seller(SystemUser.builder().id(dto.sellerId()).build())
                .artworkHeightCm(dto.artworkHeightCm())
                .artworkWidthCm(dto.artworkWidthCm())
                .discountAmount(dto.discountAmount())
                .build();
    }

    private ArtOrderResponseDTO toResponse(ArtOrder o) {
        return new ArtOrderResponseDTO(
                o.getId(), o.getOrderDate(),
                o.getArtworkHeightCm(), o.getArtworkWidthCm(),
                o.getArtwork() != null ? new ArtworkResponseDTO(o.getArtwork().getId(), o.getArtwork().getTitle(), o.getArtwork().getArtist(), o.getArtwork().getDescription(), o.getArtwork().getPrice(), o.getArtwork().getStockQuantity(), o.getArtwork().getImageUrl(), o.getArtwork().getSource()) : null,
                o.getFrame() != null ? new FrameResponseDTO(o.getFrame().getId(), o.getFrame().getCode(), o.getFrame().getName(), o.getFrame().getMaterial(), o.getFrame().getColor(), o.getFrame().getFaceWidthCm(), o.getFrame().getPricePerMeter(), o.getFrame().getStockMeters()) : null,
                o.getGlass() != null ? new GlassResponseDTO(o.getGlass().getId(), o.getGlass().getType(), o.getGlass().getDescription(), o.getGlass().getPricePerSquareMeter()) : null,
                o.getSeller() != null ? new UserResponseDTO(o.getSeller().getId(), o.getSeller().getName(), o.getSeller().getEmail(), o.getSeller().getRole(), o.getSeller().isActive()) : null,
                o.getDiscountAmount(),
                o.calculateTotalValue()
        );
    }
}
