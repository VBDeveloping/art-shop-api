package br.com.vbartshop.art_shop_api.entrypoints.controller;

import br.com.vbartshop.art_shop_api.business.model.Artwork;
import br.com.vbartshop.art_shop_api.business.service.ArtworkService;
import br.com.vbartshop.art_shop_api.entrypoints.dto.ArtworkRequestDTO;
import br.com.vbartshop.art_shop_api.entrypoints.dto.ArtworkResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/art-works")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService service;

    @GetMapping
    public ResponseEntity<List<ArtworkResponseDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream()
                .map(this::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtworkResponseDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ArtworkResponseDTO> create(@Valid @RequestBody ArtworkRequestDTO dto) {
        Artwork saved = service.save(toModel(dto));
        return ResponseEntity.ok(toResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Artwork toModel(ArtworkRequestDTO dto) {
        return Artwork.builder()
                .title(dto.title()).artist(dto.artist())
                .description(dto.description()).price(dto.price())
                .stockQuantity(dto.stockQuantity()).imageUrl(dto.imageUrl())
                .source(dto.source()).build();
    }

    private ArtworkResponseDTO toResponse(Artwork a) {
        return new ArtworkResponseDTO(a.getId(), a.getTitle(), a.getArtist(),
                a.getDescription(), a.getPrice(), a.getStockQuantity(),
                a.getImageUrl(), a.getSource());
    }
}