package br.com.vbartshop.art_shop_api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    private String phone;

    @Column(unique = true)
    private String cpf;

    private String address;

    private boolean active = true;
}