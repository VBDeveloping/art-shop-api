package br.com.vbartshop.art_shop_api.business.service;

import br.com.vbartshop.art_shop_api.business.model.Customer;
import br.com.vbartshop.art_shop_api.infrastructure.entity.CustomerEntity;
import br.com.vbartshop.art_shop_api.infrastructure.mapper.CustomerMapper;
import br.com.vbartshop.art_shop_api.infrastructure.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerService")
class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerService service;

    private CustomerEntity entity;
    private Customer model;

    @BeforeEach
    void setUp() {
        entity = CustomerEntity.builder()
                .id(1L)
                .name("João Silva")
                .email("joao@email.com")
                .cpf("123.456.789-00")
                .phone("81999999999")
                .active(true)
                .build();

        model = Customer.builder()
                .id(1L)
                .name("João Silva")
                .email("joao@email.com")
                .cpf("123.456.789-00")
                .phone("81999999999")
                .active(true)
                .build();
    }

    @Test
    @DisplayName("deve salvar cliente com active true")
    void save_deveDefinirActiveTrue() {
        model.setActive(false);

        when(repository.findByCpf(any())).thenReturn(Optional.empty());
        when(repository.findByEmail(any())).thenReturn(Optional.empty());
        when(mapper.toEntity(any())).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(model);

        service.save(model);

        assertThat(model.isActive()).isTrue();
    }

    @Test
    @DisplayName("deve lançar exceção ao salvar cliente com CPF duplicado")
    void save_deveLancarExcecao_quandoCpfJaCadastrado() {
        when(repository.findByCpf("123.456.789-00")).thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> service.save(model))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("CPF já cadastrado");
    }

    @Test
    @DisplayName("deve lançar exceção ao salvar cliente com e-mail duplicado")
    void save_deveLancarExcecao_quandoEmailJaCadastrado() {
        when(repository.findByCpf(any())).thenReturn(Optional.empty());
        when(repository.findByEmail("joao@email.com")).thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> service.save(model))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("E-mail já cadastrado");
    }

    @Test
    @DisplayName("deve alternar status do cliente")
    void toggleStatus_deveInverterAtivo() {
        entity.setActive(true);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        service.toggleStatus(1L);

        assertThat(entity.isActive()).isFalse();
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("deve lançar exceção ao alternar status de cliente inexistente")
    void toggleStatus_deveLancarExcecao_quandoClienteNaoExistir() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.toggleStatus(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("não encontrado");
    }
}