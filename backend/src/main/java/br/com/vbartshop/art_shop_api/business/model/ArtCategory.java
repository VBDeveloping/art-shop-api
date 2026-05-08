package br.com.vbartshop.art_shop_api.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtCategory {
    private Long id;
    private String name;        // Ex: "Abstrato", "Clássico", "Digital"
    private String description; // Opcional: Detalhes sobre a categoria
}
