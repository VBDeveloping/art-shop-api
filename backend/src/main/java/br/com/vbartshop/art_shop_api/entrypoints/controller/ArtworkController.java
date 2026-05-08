package br.com.vbartshop.art_shop_api.entrypoints.controller;

import br.com.vbartshop.art_shop_api.business.model.Artwork;
import br.com.vbartshop.art_shop_api.business.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/art-works")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ArtworkController {

    private final ArtworkService service;

    @GetMapping
    public ResponseEntity<List<Artwork>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artwork> getbyId(@PathVariable Long id) {
        return service.findById(id)
                .map(art -> ResponseEntity.ok().body(art))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Artwork> create(@RequestBody Artwork artwork) {
        return ResponseEntity.ok(service.save(artwork));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
