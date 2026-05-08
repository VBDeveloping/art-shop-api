package br.com.vbartshop.art_shop_api.entrypoints.controller;

import br.com.vbartshop.art_shop_api.business.model.Frame;
import br.com.vbartshop.art_shop_api.business.service.FrameService;
import br.com.vbartshop.art_shop_api.entrypoints.dto.FrameRequestDTO;
import br.com.vbartshop.art_shop_api.entrypoints.dto.FrameResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/frames")
@RequiredArgsConstructor
public class FrameController {

    private final FrameService service;

    @GetMapping
    public ResponseEntity<List<FrameResponseDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream()
                .map(this::toResponse).toList());
    }

    @PostMapping
    public ResponseEntity<FrameResponseDTO> create(@Valid @RequestBody FrameRequestDTO dto) {
        Frame saved = service.save(toModel(dto));
        return ResponseEntity.ok(toResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Frame toModel(FrameRequestDTO dto) {
        return Frame.builder()
                .code(dto.code()).name(dto.name()).material(dto.material())
                .color(dto.color()).faceWidthCm(dto.faceWidthCm())
                .cuttingMarginCm(dto.cuttingMarginCm())
                .pricePerMeter(dto.pricePerMeter()).stockMeters(dto.stockMeters())
                .build();
    }

    private FrameResponseDTO toResponse(Frame f) {
        return new FrameResponseDTO(f.getId(), f.getCode(), f.getName(),
                f.getMaterial(), f.getColor(), f.getFaceWidthCm(),
                f.getPricePerMeter(), f.getStockMeters());
    }
}
