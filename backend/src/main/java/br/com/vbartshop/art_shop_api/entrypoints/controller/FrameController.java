package br.com.vbartshop.art_shop_api.entrypoints.controller;

import br.com.vbartshop.art_shop_api.business.model.Frame;
import br.com.vbartshop.art_shop_api.business.service.FrameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/frames")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FrameController {

    private final FrameService service;

    @GetMapping
    public ResponseEntity<List<Frame>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Frame> create(@RequestBody Frame frame) {
        return ResponseEntity.ok(service.save(frame));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
