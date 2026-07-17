E-commerce Backend API

API REST desenvolvida para gerenciamento de um sistema de e-commerce, incluindo autenticação de usuários, catálogo de produtos e processamento de pedidos.

TECNOLOGIAS:
  Java 17
  Spring Boot
  Spring Security (JWT / OAuth2)
  JPA / Hibernate
  PostgreSQL

FUNCIONALIDADES:

  Cadastro e autenticação de usuários
  Controle de permissões (roles)
  Listagem de produtos
  Carrinho de compras
  Criação de pedidos
  Integração com banco de dados relacional

AUTENTICAÇÃO:

  A API utiliza autenticação baseada em JWT.
  
  Obter token:
  
  POST /auth/login
  
  Usar token:
  Authorization: Bearer <token>

APRENDIZADOS:
  Implementação de autenticação com JWT
  Organização em camadas (Controller, Service, Repository)
  Boas práticas com Spring Boot
  Tratamento de exceções e segurança
