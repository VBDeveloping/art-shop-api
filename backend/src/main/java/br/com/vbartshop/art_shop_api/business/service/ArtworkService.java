package br.com.vbartshop.art_shop_api.business.service;

import br.com.vbartshop.art_shop_api.business.model.Artwork;
import br.com.vbartshop.art_shop_api.infrastructure.entity.ArtworkEntity;
import br.com.vbartshop.art_shop_api.infrastructure.mapper.ArtworkMapper;
import br.com.vbartshop.art_shop_api.infrastructure.repository.ArtworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    private final ArtworkRepository repository;
    private final ArtworkMapper mapper;

    // O repository retorna Entity
    public List<Artwork> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }

    // Busca a Entity e converte para Model se existir
    public Optional<Artwork> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toModel);
    }

    // Entra a regra de negócio e o uso do toEntity
    public Artwork save(Artwork artwork) {
        if (artwork.getStockQuantity()==null){
            artwork.setStockQuantity(0);
        }
        // Model -> Entity
        ArtworkEntity entity = mapper.toEntity(artwork);

        // Salva Entity
        ArtworkEntity savedEntity = repository.save(entity);

        // Retorna Model traduzido de volta
        return mapper.toModel(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
