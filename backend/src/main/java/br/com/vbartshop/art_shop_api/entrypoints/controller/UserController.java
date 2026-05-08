package br.com.vbartshop.art_shop_api.entrypoints.controller;

import br.com.vbartshop.art_shop_api.business.model.SystemUser;
import br.com.vbartshop.art_shop_api.business.service.UserService;
import br.com.vbartshop.art_shop_api.entrypoints.dto.UserRequestDTO;
import br.com.vbartshop.art_shop_api.entrypoints.dto.UserResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream()
                .map(this::toResponse).toList());
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO dto) {
        SystemUser saved = service.save(toModel(dto));
        return ResponseEntity.ok(toResponse(saved));
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Void> toggleStatus(@PathVariable Long id) {
        service.toggleUserStatus(id);
        return ResponseEntity.noContent().build();
    }

    private SystemUser toModel(UserRequestDTO dto) {
        return SystemUser.builder()
                .name(dto.name()).email(dto.email())
                .password(dto.password()).role(dto.role()).build();
    }

    private UserResponseDTO toResponse(SystemUser u) {
        return new UserResponseDTO(u.getId(), u.getName(),
                u.getEmail(), u.getRole(), u.isActive());
    }
}