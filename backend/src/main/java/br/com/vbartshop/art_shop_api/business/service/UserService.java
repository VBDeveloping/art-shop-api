package br.com.vbartshop.art_shop_api.business.service;

import br.com.vbartshop.art_shop_api.business.model.SystemUser;
import br.com.vbartshop.art_shop_api.infrastructure.entity.UserEntity;
import br.com.vbartshop.art_shop_api.infrastructure.mapper.UserMapper;
import br.com.vbartshop.art_shop_api.infrastructure.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Transactional(readOnly = true)
    public List<SystemUser> findAll() {
        return repository.findAll().stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public SystemUser save(SystemUser user) {
        // Regra de Negócio: Verificar se o e-mail já existe
        repository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Este e-mail já está cadastrado no sistema.");
        });

        // IMPORTANTE: Por enquanto salvamos a senha assim,
        // mas em breve usaremos BCrypt para segurança.
        UserEntity entity = mapper.toEntity(user);
        return mapper.toModel(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public SystemUser findByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toModel)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Transactional
    public void toggleUserStatus(Long id) {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Inverte o status de ativo/inativo
        entity.setActive(!entity.isActive());
        repository.save(entity);
    }
}
