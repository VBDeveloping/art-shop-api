package br.com.vbartshop.art_shop_api.business.service;

import br.com.vbartshop.art_shop_api.business.model.ArtOrder;
import br.com.vbartshop.art_shop_api.business.model.Artwork;
import br.com.vbartshop.art_shop_api.business.model.Frame;
import br.com.vbartshop.art_shop_api.business.model.enums.OrderStatus;
import br.com.vbartshop.art_shop_api.infrastructure.entity.ArtOrderEntity;
import br.com.vbartshop.art_shop_api.infrastructure.mapper.ArtOrderMapper;
import br.com.vbartshop.art_shop_api.infrastructure.repository.ArtOrderRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtOrderService {

    private final ArtOrderRepository repository;
    private final ArtOrderMapper mapper;
    private final FrameService frameService;
    private final ArtworkService artworkService;

    public List<ArtOrder> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public ArtOrder createOrder(ArtOrder order) {

        validateInput(order);

        Artwork artwork = artworkService.findById(order.getArtwork().getId())
                .orElseThrow(() -> new RuntimeException("Arte não encontrada com ID: " + order.getArtwork().getId()));

        Frame frame = frameService.findById(order.getFrame().getId())
                .orElseThrow(() -> new RuntimeException("Moldura não encontrada com ID: " + order.getFrame().getId()));

        if (!artwork.isAvailableForOrder()) {
            throw new RuntimeException("Esta arte não possui estoque disponível para venda.");
        }

        order.setArtwork(artwork);
        order.setFrame(frame);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.calculateTotalValue();

        frameService.deductStock(frame.getId(), order.calculatePerimeterInMeters());

        ArtOrderEntity entity = mapper.toEntity(order);
        return mapper.toModel(repository.save(entity));
    }

    @Transactional
    public ArtOrder updateStatus(Long id, OrderStatus status) {
        ArtOrderEntity entity = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Pedido não encontrado."));
        entity.setStatus(status);
        return mapper.toModel(repository.save(entity));
    }

    private void validateInput(ArtOrder order) {
        if (order.getArtwork() == null || order.getArtwork().getId() == null)
            throw new RuntimeException("O campo 'artwork' com um 'id' válido é obrigatório.");
        if (order.getFrame() == null || order.getFrame().getId() == null)
            throw new RuntimeException("O campo 'frame' com um 'id' válido é obrigatório.");
        if (order.getCustomer() == null || order.getCustomer().getId() == null)
            throw new RuntimeException("O campo 'customerId' é obrigatório.");
        if (order.getDeliveryDate() == null)
            throw new RuntimeException("O campo 'deliveryDate' é obrigatório.");
    }

    public Optional<ArtOrder> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toModel);
    }
}
