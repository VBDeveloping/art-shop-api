package br.com.vbartshop.art_shop_api.business.service;

import br.com.vbartshop.art_shop_api.business.model.Artwork;
import br.com.vbartshop.art_shop_api.business.model.enums.ArtSource;
import br.com.vbartshop.art_shop_api.infrastructure.entity.ArtworkEntity;
import br.com.vbartshop.art_shop_api.infrastructure.mapper.ArtworkMapper;
import br.com.vbartshop.art_shop_api.infrastructure.repository.ArtworkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ArtworkService")
class ArtworkServiceTest {

    @Mock
    private ArtworkRepository repository;

    @Mock
    private ArtworkMapper mapper;

    @InjectMocks
    private ArtworkService service;

    private ArtworkEntity entity;
    private Artwork model;

    @BeforeEach
    void setUp() {
        entity = ArtworkEntity.builder()
                .id(1L)
                .title("Pôr do Sol em Recife")
                .artist("Victor Bezerra")
                .price(new BigDecimal("350.00"))
                .stockQuantity(3)
                .source(ArtSource.STORE_INVENTORY)
                .build();

        model = Artwork.builder()
                .id(1L)
                .title("Pôr do Sol em Recife")
                .artist("Victor Bezerra")
                .price(new BigDecimal("350.00"))
                .stockQuantity(3)
                .source(ArtSource.STORE_INVENTORY)
                .build();
    }

    @Test
    @DisplayName("deve listar todas as obras")
    void findAll_deveRetornarListaDeObras() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toModel(entity)).thenReturn(model);

        List<Artwork> result = service.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Pôr do Sol em Recife");
        verify(repository).findAll();
    }

    @Test
    @DisplayName("deve buscar obra por ID quando existir")
    void findById_deveRetornarObra_quandoExistir() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toModel(entity)).thenReturn(model);

        Optional<Artwork> result = service.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("deve retornar vazio quando obra não existir")
    void findById_deveRetornarVazio_quandoNaoExistir() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Artwork> result = service.findById(99L);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("deve salvar obra e definir estoque zero quando nulo")
    void save_deveDefinirEstoqueZero_quandoStockQuantityForNulo() {
        model.setStockQuantity(null);

        when(mapper.toEntity(any())).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(model);

        service.save(model);

        assertThat(model.getStockQuantity()).isEqualTo(0);
    }

    @Test
    @DisplayName("deve adicionar estoque corretamente")
    void addStock_deveIncrementarEstoque() {
        entity.setStockQuantity(5);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(model);

        service.addStock(1L, 10);

        assertThat(entity.getStockQuantity()).isEqualTo(15);
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("deve lançar exceção ao adicionar estoque com quantidade inválida")
    void addStock_deveLancarExcecao_quandoQuantidadeInvalida() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> service.addStock(1L, 0))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("maior que zero");

        assertThatThrownBy(() -> service.addStock(1L, -5))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("maior que zero");
    }

    @Test
    @DisplayName("deve lançar exceção ao adicionar estoque em obra inexistente")
    void addStock_deveLancarExcecao_quandoObraNaoExistir() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.addStock(99L, 5))
                .isInstanceOf(RuntimeException.class);
    }
}