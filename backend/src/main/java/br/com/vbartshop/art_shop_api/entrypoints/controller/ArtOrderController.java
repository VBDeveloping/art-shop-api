package br.com.vbartshop.art_shop_api.entrypoints.controller;

import br.com.vbartshop.art_shop_api.business.model.ArtOrder;
import br.com.vbartshop.art_shop_api.business.service.ArtOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/art-orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ArtOrderController {

    private final ArtOrderService service;

    @PostMapping
    public ResponseEntity<ArtOrder> placeOrder(@RequestBody ArtOrder order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(order));
    }

    @GetMapping
    public ResponseEntity<List<ArtOrder>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtOrder> getById(@PathVariable Long id) {

        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
