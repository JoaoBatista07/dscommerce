# DSCommerce

API REST de e-commerce em Java e Spring Boot, desenvolvida a partir do projeto do curso **Java Spring Professional**, da DevSuperior (professor Nelio Alves), como parte do meu aprendizado de back-end.

> **Status:** em desenvolvimento. O projeto já foi implantado no Railway, mas a versão online está fora do ar no momento e será republicada junto com o front-end.

## Sobre o projeto

O DSCommerce simula o back-end de uma loja virtual. Ele expõe recursos para consultar produtos (com paginação e ordenação), listar categorias, consultar pedidos e obter os dados do usuário autenticado. O acesso é protegido por OAuth2, com tokens JWT.

O objetivo é praticar os fundamentos de um back-end profissional: modelagem de domínio, relacionamentos entre entidades, segurança, validação de dados, tratamento padronizado de erros e organização em camadas.

## Tecnologias

- Java 17
- Spring Boot 4.0.6 (Spring Web MVC)
- Spring Data JPA e Hibernate
- Spring Security, Spring Authorization Server (OAuth2) e Spring Resource Server (JWT)
- Bean Validation (Hibernate Validator)
- H2 (perfil `test`) e PostgreSQL (perfis `dev` e `prod`)
- Maven, com Maven Wrapper

## Arquitetura

O projeto segue uma organização em camadas, em `src/main/java/com/devsuperior/dscommerce`:

| Pacote | Responsabilidade |
|---|---|
| `controllers` | Endpoints REST (`Category`, `Order`, `Product` e `User`). Em `handlers`, o `ControllerExceptionHandler` centraliza as respostas de erro |
| `services` | Regras de negócio e apoio à autenticação (`AuthService`). Em `exceptions`, as exceções de serviço (recurso não encontrado, erro de banco e acesso negado) |
| `repositories` | Acesso a dados com Spring Data JPA |
| `entities` | Modelo de domínio e mapeamento ORM: `User`, `Role`, `Product`, `Category`, `Order`, `OrderItem`, `Payment` e `OrderStatus` |
| `dto` | Contratos de entrada e saída da API, incluindo `CustomError` e `ValidationError` para erros padronizados |
| `projections` | Projeção usada para carregar o usuário e seus perfis na autenticação |
| `config` | Configuração do Authorization Server e do Resource Server, CORS (`WebConfig`) e o grant de senha customizado (`customgrant`) |

## Segurança

- A autenticação segue o fluxo OAuth2 com um **grant de senha customizado**, implementado em `config/customgrant`. A aplicação emite um token JWT.
- O cliente se identifica com *Basic Auth* (`client-id` e `client-secret`), e o usuário informa `username` e `password`.
- As rotas protegidas validam o JWT enviado no cabeçalho `Authorization: Bearer <token>`.
- A duração do token e as origens permitidas no CORS são configuráveis por variáveis de ambiente (veja a tabela abaixo).

## Endpoints

| Método | Rota | Acesso |
|---|---|---|
| `POST` | `/oauth2/token` | Basic Auth do cliente |
| `GET` | `/products` | Público |
| `GET` | `/products/{id}` | Público |
| `PUT` | `/products/{id}` | Autenticado |
| `DELETE` | `/products/{id}` | Autenticado |
| `GET` | `/categories` | Público |
| `GET` | `/users/me` | Autenticado |
| `GET` | `/orders/{id}` | Autenticado |

A listagem de produtos aceita paginação e ordenação, por exemplo: `GET /products?page=0&size=12&sort=name,desc`.

## Como executar

**Pré-requisitos:** Java 17 e Git. O Maven não precisa estar instalado, porque o projeto usa o Maven Wrapper.

```bash
# clonar o repositório
git clone https://github.com/JoaoBatista07/dscommerce.git
cd dscommerce

# executar (no PowerShell, use .\mvnw spring-boot:run)
./mvnw spring-boot:run
```

Por padrão, a aplicação roda com o perfil `test`: banco **H2 em memória**, populado a partir de `src/main/resources/import.sql`. Ela fica disponível em `http://localhost:8080`.

O console do H2 fica em `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:testdb`, usuário `sa`, sem senha).

## Perfis e variáveis de ambiente

O perfil ativo é escolhido pela variável `APP_PROFILE`.

| Perfil | Banco de dados |
|---|---|
| `test` (padrão) | H2 em memória |
| `dev` | PostgreSQL local (`localhost:5433`, banco `dscommerce`) |
| `prod` | PostgreSQL configurado por variáveis de ambiente |

| Variável | Padrão | Uso |
|---|---|---|
| `APP_PROFILE` | `test` | Perfil ativo |
| `CLIENT_ID` | `myclientid` | Identificador do cliente OAuth2 |
| `CLIENT_SECRET` | `myclientsecret` | Segredo do cliente OAuth2 |
| `JWT_DURATION` | `86400` | Duração do token, em segundos |
| `CORS_ORIGINS` | `http://localhost:3000,http://localhost:5173` | Origens permitidas no CORS |
| `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` | sem padrão | Conexão com o PostgreSQL (perfil `prod`) |

> Os valores padrão de `CLIENT_ID` e `CLIENT_SECRET` servem **apenas para desenvolvimento local**. Em qualquer ambiente publicado, defina valores próprios.

## Como testar a API

1. Obtenha um token com `POST /oauth2/token`:
    - **Authorization:** *Basic Auth*, com o `client-id` e o `client-secret` como usuário e senha.
    - **Body** (`x-www-form-urlencoded`): `username`, `password` e `grant_type=password`.
2. Use o `access_token` retornado nas rotas protegidas, com o cabeçalho `Authorization: Bearer <token>`.

Os dados de exemplo, incluindo os usuários, ficam em `src/main/resources/import.sql`.

## Testes

```bash
./mvnw test
```

## Próximos passos

- Ampliar os testes automatizados.
- Republicar a API online, junto com o front-end.

## Autor

**João Vitor Bispo Batista**
[Portfólio](https://joaobatista07.github.io) · [LinkedIn](https://www.linkedin.com/in/jo%C3%A3o-bispo-batista/) · [GitHub](https://github.com/JoaoBatista07)

## Créditos

Projeto-base do curso **Java Spring Professional**, da [DevSuperior](https://devsuperior.com.br), ministrado pelo professor Nelio Alves.
