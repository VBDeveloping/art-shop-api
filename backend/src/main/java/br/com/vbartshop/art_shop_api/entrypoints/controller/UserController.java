package br.com.vbartshop.art_shop_api.entrypoints.controller;

import br.com.vbartshop.art_shop_api.business.model.SystemUser;
import br.com.vbartshop.art_shop_api.business.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<SystemUser>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<SystemUser> create(@RequestBody SystemUser user) {
        return ResponseEntity.ok(service.save(user));
    }

    // Rota especial para ativar/desativar usuário
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Void> toggleStatus(@PathVariable Long id) {
        service.toggleUserStatus(id);
        return ResponseEntity.noContent().build();
    }
}