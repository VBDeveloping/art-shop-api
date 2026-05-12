# 🖼️ Art Shop API

REST API para gerenciamento de uma loja de molduras e impressões artísticas. Desenvolvida com Java 21 e Spring Boot, com autenticação JWT, controle de acesso por perfil de usuário e fluxo completo de atendimento ao cliente.

---

## 🚀 Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 3.4.x |
| Spring Security | 7.x |
| JWT (JJWT) | 0.13.0 |
| PostgreSQL | 15+ |
| Hibernate / JPA | — |
| Lombok | — |
| Maven | — |

---

## 📐 Arquitetura

O projeto segue uma arquitetura em camadas inspirada na **arquitetura hexagonal**, com separação clara de responsabilidades:

```
src/main/java/br/com/vbartshop/art_shop_api/
├── business/
│   ├── model/          # Entidades de domínio (sem JPA)
│   │   └── enums/      # Enumerações de negócio
│   └── service/        # Regras de negócio
├── entrypoints/
│   ├── controller/     # Controllers REST
│   └── dto/            # DTOs de request e response
└── infrastructure/
    ├── entity/         # Entidades JPA
    ├── mapper/         # Conversão entre model e entity
    ├── repository/     # Interfaces Spring Data JPA
    ├── security/       # JWT, filtros e configuração de segurança
    └── exception/      # Tratamento global de erros
```

---

## 🔐 Autenticação e Autorização

A API utiliza **JWT (JSON Web Token)** para autenticação stateless. O token é gerado no login e deve ser enviado em todas as requisições protegidas via header `Authorization: Bearer <token>`.

### Perfis de acesso

| Role | Permissões |
|---|---|
| `ADMIN` | Acesso total, incluindo gestão de usuários |
| `SELLER` | Criar pedidos e gerenciar clientes |
| `STOCK` | Gerenciar produtos e estoque |
| `FINANCIAL` | Visualizar pagamentos e faturamento |

---

## 📦 Funcionalidades

- ✅ Autenticação JWT com BCrypt
- ✅ Controle de acesso por role
- ✅ Gestão de usuários (ADMIN)
- ✅ Cadastro e gerenciamento de clientes
- ✅ Catálogo de obras de arte, molduras e vidros
- ✅ Criação de pedidos com cálculo automático de valor
- ✅ Controle de estoque com dedução automática
- ✅ Registro de pagamentos (PIX, Cartão, Dinheiro)
- ✅ Status de pedido (PENDING, IN_PROGRESS, READY, DELIVERED)
- ✅ Prazo de entrega por pedido
- ✅ Tratamento global de exceções

---

## ⚙️ Como rodar localmente

### Pré-requisitos

- Java 21+
- Maven 3.8+
- PostgreSQL 15+

### 1. Clone o repositório

```bash
git clone https://github.com/VBDeveloping/art-shop-api.git
cd art-shop-api/backend
```

### 2. Configure as variáveis de ambiente

Crie o arquivo `src/main/resources/application-local.properties` baseado no exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/vbartshop_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
api.security.token.secret=seu_secret_jwt_aqui_minimo_32_caracteres
```

### 3. Crie o banco de dados

```sql
CREATE DATABASE vbartshop_db;
```

### 4. Rode a aplicação

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

A API estará disponível em `http://localhost:8080`.

### 5. Crie o primeiro usuário ADMIN via SQL

```sql
INSERT INTO tb_users (name, email, password, role, active)
VALUES (
  'Admin',
  'admin@vbartshop.com',
  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LqTFOo4ubOC',
  'ADMIN',
  true
);
-- Senha: 123456
```

---

## 🗺️ Endpoints

### Auth
| Método | Endpoint | Descrição | Acesso |
|---|---|---|---|
| POST | `/api/auth/login` | Autenticar e obter token JWT | Público |

### Usuários
| Método | Endpoint | Descrição | Acesso |
|---|---|---|---|
| GET | `/api/users` | Listar usuários | ADMIN |
| POST | `/api/users` | Criar usuário | ADMIN |
| PATCH | `/api/users/{id}/toggle-status` | Ativar/desativar usuário | ADMIN |

### Clientes
| Método | Endpoint | Descrição | Acesso |
|---|---|---|---|
| GET | `/api/customers` | Listar clientes | ADMIN, SELLER |
| GET | `/api/customers/{id}` | Buscar cliente por ID | ADMIN, SELLER |
| POST | `/api/customers` | Cadastrar cliente | ADMIN, SELLER |
| PUT | `/api/customers/{id}` | Atualizar cliente | ADMIN, SELLER |
| PATCH | `/api/customers/{id}/toggle-status` | Ativar/desativar cliente | ADMIN, SELLER |

### Obras de Arte
| Método | Endpoint | Descrição | Acesso |
|---|---|---|---|
| GET | `/api/art-works` | Listar obras | ADMIN, STOCK |
| GET | `/api/art-works/{id}` | Buscar obra por ID | ADMIN, STOCK |
| POST | `/api/art-works` | Cadastrar obra | ADMIN, STOCK |
| DELETE | `/api/art-works/{id}` | Remover obra | ADMIN, STOCK |
| PATCH | `/api/art-works/{id}/add-stock?quantity=N` | Adicionar estoque | ADMIN, STOCK |

### Molduras
| Método | Endpoint | Descrição | Acesso |
|---|---|---|---|
| GET | `/api/frames` | Listar molduras | ADMIN, STOCK |
| POST | `/api/frames` | Cadastrar moldura | ADMIN, STOCK |
| DELETE | `/api/frames/{id}` | Remover moldura | ADMIN, STOCK |
| PATCH | `/api/frames/{id}/add-stock?meters=N` | Adicionar estoque | ADMIN, STOCK |

### Vidros
| Método | Endpoint | Descrição | Acesso |
|---|---|---|---|
| GET | `/api/glasses` | Listar vidros | ADMIN, STOCK |
| POST | `/api/glasses` | Cadastrar vidro | ADMIN, STOCK |

### Pedidos
| Método | Endpoint | Descrição | Acesso |
|---|---|---|---|
| GET | `/api/art-orders` | Listar pedidos | ADMIN, SELLER |
| GET | `/api/art-orders/{id}` | Buscar pedido por ID | ADMIN, SELLER |
| POST | `/api/art-orders` | Criar pedido | ADMIN, SELLER |
| PATCH | `/api/art-orders/{id}/status?status=X` | Atualizar status | ADMIN, SELLER |

### Pagamentos
| Método | Endpoint | Descrição | Acesso |
|---|---|---|---|
| GET | `/api/payments/order/{orderId}` | Listar pagamentos de um pedido | ADMIN, FINANCIAL |
| POST | `/api/payments` | Registrar pagamento | ADMIN, FINANCIAL |
| PATCH | `/api/payments/{id}/cancel` | Cancelar pagamento | ADMIN, FINANCIAL |

---

## 📋 Exemplos de requisição

### Login
```json
POST /api/auth/login
{
  "email": "admin@vbartshop.com",
  "password": "123456"
}
```

### Criar cliente
```json
POST /api/customers
Authorization: Bearer <token>
{
  "name": "João Silva",
  "email": "joao@email.com",
  "phone": "81999999999",
  "cpf": "123.456.789-00",
  "address": "Rua das Flores, 123 - Recife/PE"
}
```

### Criar pedido
```json
POST /api/art-orders
Authorization: Bearer <token>
{
  "artworkId": 1,
  "frameId": 1,
  "glassId": 1,
  "sellerId": 1,
  "customerId": 1,
  "artworkHeightCm": 40.0,
  "artworkWidthCm": 30.0,
  "deliveryDate": "2026-05-20",
  "discountAmount": 10.00
}
```

### Registrar pagamento
```json
POST /api/payments
Authorization: Bearer <token>
{
  "orderId": 1,
  "amount": 340.00,
  "type": "PIX",
  "notes": "Pagamento via PIX"
}
```

---

## 🧠 Regras de negócio

- O valor total do pedido é calculado automaticamente com base na obra, moldura e vidro selecionados
- O estoque de moldura é deduzido automaticamente ao criar um pedido (cálculo por perímetro com margem de corte)
- Obras do tipo `CUSTOMER_OWNED` não têm custo no pedido
- O prazo de entrega é definido pelo vendedor no momento do pedido
- Um pedido pode ter múltiplos pagamentos (parcelamento em diferentes formas)

---

## 👨‍💻 VBDeveloping

**Victor Bezerra**
- GitHub: [@VBDeveloping](https://github.com/VBDeveloping)
- Projeto: [art-shop-api](https://github.com/VBDeveloping/art-shop-api)
