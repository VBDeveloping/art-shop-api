package br.com.vbartshop.art_shop_api.business.service;

import br.com.vbartshop.art_shop_api.business.model.SystemUser;
import br.com.vbartshop.art_shop_api.business.model.enums.UserRole;
import br.com.vbartshop.art_shop_api.infrastructure.entity.UserEntity;
import br.com.vbartshop.art_shop_api.infrastructure.mapper.UserMapper;
import br.com.vbartshop.art_shop_api.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService")
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;

    private UserEntity entity;
    private SystemUser model;

    @BeforeEach
    void setUp() {
        entity = UserEntity.builder()
                .id(1L)
                .name("Victor Bezerra")
                .email("victor@vbartshop.com")
                .password("$2a$10$hashedpassword")
                .role(UserRole.ADMIN)
                .active(true)
                .build();

        model = SystemUser.builder()
                .id(1L)
                .name("Victor Bezerra")
                .email("victor@vbartshop.com")
                .password("123456")
                .role(UserRole.ADMIN)
                .active(true)
                .build();
    }

    @Test
    @DisplayName("deve salvar usuário com senha criptografada")
    void save_deveCriptografarSenha() {
        when(repository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("$2a$10$hashedpassword");
        when(mapper.toEntity(any())).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(model);

        service.save(model);

        verify(passwordEncoder).encode("123456");
        assertThat(model.getPassword()).isEqualTo("$2a$10$hashedpassword");
    }

    @Test
    @DisplayName("deve lançar exceção ao salvar usuário com e-mail duplicado")
    void save_deveLancarExcecao_quandoEmailJaCadastrado() {
        when(repository.findByEmail("victor@vbartshop.com")).thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> service.save(model))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("já está cadastrado");
    }

    @Test
    @DisplayName("deve alternar status do usuário")
    void toggleUserStatus_deveInverterAtivo() {
        entity.setActive(true);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        service.toggleUserStatus(1L);

        assertThat(entity.isActive()).isFalse();
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("deve lançar exceção ao alternar status de usuário inexistente")
    void toggleUserStatus_deveLancarExcecao_quandoUsuarioNaoExistir() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.toggleUserStatus(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("não encontrado");
    }
}