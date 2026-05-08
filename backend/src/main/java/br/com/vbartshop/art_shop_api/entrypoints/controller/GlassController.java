package br.com.vbartshop.art_shop_api.entrypoints.controller;

import br.com.vbartshop.art_shop_api.business.model.Glass;
import br.com.vbartshop.art_shop_api.business.service.GlassService;
import br.com.vbartshop.art_shop_api.entrypoints.dto.GlassRequestDTO;
import br.com.vbartshop.art_shop_api.entrypoints.dto.GlassResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/glasses")
@RequiredArgsConstructor
public class GlassController {

    private final GlassService service;

    @GetMapping
    public ResponseEntity<List<GlassResponseDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream()
                .map(this::toResponse).toList());
    }

    @PostMapping
    public ResponseEntity<GlassResponseDTO> create(@Valid @RequestBody GlassRequestDTO dto) {
        Glass saved = service.save(toModel(dto));
        return ResponseEntity.ok(toResponse(saved));
    }

    private Glass toModel(GlassRequestDTO dto) {
        return Glass.builder()
                .type(dto.type()).description(dto.description())
                .pricePerSquareMeter(dto.pricePerSquareMeter())
                .technicalGapCm(dto.technicalGapCm()).build();
    }

    private GlassResponseDTO toResponse(Glass g) {
        return new GlassResponseDTO(g.getId(), g.getType(),
                g.getDescription(), g.getPricePerSquareMeter());
    }
}
