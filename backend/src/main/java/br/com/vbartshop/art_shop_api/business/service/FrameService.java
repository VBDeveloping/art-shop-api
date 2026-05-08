package br.com.vbartshop.art_shop_api.business.service;


import br.com.vbartshop.art_shop_api.business.model.Frame;
import br.com.vbartshop.art_shop_api.infrastructure.entity.FrameEntity;
import br.com.vbartshop.art_shop_api.infrastructure.mapper.FrameMapper;
import br.com.vbartshop.art_shop_api.infrastructure.repository.FrameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FrameService {
    private final FrameRepository repository;
    private final FrameMapper mapper;

    @Transactional(readOnly = true)
    public List<Frame> findAll(){
        return repository.findAll()
                .stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Frame> findById(Long id){
        return repository.findById(id)
                .map(mapper::toModel);
    }

    @Transactional
    public Frame save(Frame frame){
        // Se o estoque não for informado, começa com zero
        if (frame.getStockMeters() == null){
            frame.setStockMeters(0);
        }
        FrameEntity entity = mapper.toEntity(frame);
        return  mapper.toModel(repository.save(entity));
    }

    // Regra de Negócio: Atualiza o estoque após uma venda
    @Transactional
    public void deductStock(Long frameId, Double meterUsed){
        FrameEntity entity = repository.findById(frameId)
                .orElseThrow(()-> new RuntimeException("Moldura não encontrada."));

        int currentStock = entity.getStockMeters();

        // Arredonda para cima para garantir que não falte material
        int meterToDeduct = (int) Math.ceil(meterUsed);

        if (currentStock < meterToDeduct){
            throw new RuntimeException("Estoque insuficiente para a moldura: " + entity.getName());
        }

        entity.setStockMeters(currentStock - meterToDeduct);
        repository.save(entity);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
