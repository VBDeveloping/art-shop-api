package br.com.vbartshop.art_shop_api.business.service;


import br.com.vbartshop.art_shop_api.business.model.Glass;
import br.com.vbartshop.art_shop_api.infrastructure.mapper.GlassMapper;
import br.com.vbartshop.art_shop_api.infrastructure.repository.GlassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GlassService {
    private final GlassRepository repository;
    private final GlassMapper mapper;

    public List<Glass> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }

    public Glass save(Glass glass) {
        return mapper.toModel(repository.save(mapper.toEntity(glass)));
    }

}
