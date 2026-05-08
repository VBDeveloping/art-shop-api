package br.com.vbartshop.art_shop_api.entrypoints.controller;

import br.com.vbartshop.art_shop_api.business.model.Glass;
import br.com.vbartshop.art_shop_api.business.service.GlassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/glasses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GlassController {

    private final GlassService service;

    @GetMapping
    public ResponseEntity<List<Glass>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Glass> create(@RequestBody Glass glass) {
        return ResponseEntity.ok(service.save(glass));
    }
}
