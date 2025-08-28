# Gerenciador de Tarefas API

![Java](https://img.shields.io/badge/Java-21-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen.svg)
![Maven](https://img.shields.io/badge/Maven-4.0.0-red.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-gray.svg?logo=postgresql)
![Docker](https://img.shields.io/badge/docker-gray.svg?logo=docker)

## 📖 Sobre o Projeto

Esta é uma API RESTful completa para o gerenciamento de tarefas, projetada para demonstrar competências essenciais em desenvolvimento back-end com Java e o ecossistema Spring. A aplicação permite que usuários se cadastrem, autentiquem e controlem suas tarefas pessoais de forma segura e eficiente, utilizando autenticação baseada em JWT (JSON Web Token).

O projeto foi construído seguindo as melhores práticas de arquitetura de software, incluindo:

-   **Padrão DTO (Data Transfer Object):** Para garantir o desacoplamento entre as camadas de API e de dados, além de proteger a aplicação contra exposição de dados sensíveis.
-   **Tratamento de Exceções Centralizado:** Para lidar com erros de forma consistente e retornar respostas claras para o cliente.
-   **Containerização com Docker:** Para facilitar a configuração e a execução do ambiente de desenvolvimento.

## ✨ Funcionalidades

-   **Autenticação e Segurança:**
    -   Registro de novos usuários com senha criptografada.
    -   Login com credenciais para geração de um token de acesso JWT.
    -   Endpoints protegidos que só podem ser acessados com um token válido.
-   **Gerenciamento de Usuários:**
    -   Funcionalidades CRUD completas (Criar, Ler, Atualizar, Deletar) para usuários.
-   **Gerenciamento de Tarefas:**
    -   Funcionalidades CRUD completas para as tarefas.
    -   Cada tarefa é associada a um usuário específico.
    -   Atributos como `título`, `descrição`, `data de vencimento`, `prioridade` e `status`.

## 🛠️ Tecnologias Utilizadas

| Ferramenta                 | Descrição                                                                         |
| -------------------------- | --------------------------------------------------------------------------------- |
| **Java 21** | A versão mais recente LTS (Long-Term Support) da linguagem Java.                  |
| **Spring Boot 3** | Framework para a criação de aplicações Java robustas e de alta performance.       |
| **Spring Web** | Para a construção dos endpoints da API RESTful.                                   |
| **Spring Data JPA** | Para a persistência de dados de forma simplificada e eficiente.                   |
| **Spring Security** | Para implementar a camada de segurança e autenticação da aplicação.               |
| **PostgreSQL** | Banco de dados relacional utilizado para armazenar os dados.                      |
| **Hibernate** | Framework ORM (Object-Relational Mapping) para o mapeamento das entidades.        |
| **JWT (Java JWT)** | Biblioteca para a criação e validação dos tokens de autenticação.                 |
| **MapStruct** | Ferramenta para gerar mapeamentos entre DTOs e Entidades de forma automática.     |
| **Lombok** | Biblioteca para reduzir a verbosidade do código Java (getters, setters, etc.).    |
| **Docker & Docker Compose**| Para criar containers para a aplicação e o banco de dados.                        |
| **Maven** | Ferramenta para gerenciamento de dependências e build do projeto.                 |
| **JUnit 5 & Mockito** | Para a implementação de testes unitários e garantir a qualidade do código.        |

## 🚀 Como Executar o Projeto

### Pré-requisitos

-   **Java 21**
-   **Docker** e **Docker Compose**
-   **Maven**
-   **Git**
-   Um cliente de API como **Postman** ou **Insomnia**.

### Configurando o Ambiente

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/eduardo-ml/gerenciador-de-tarefas-springboot.git
    cd gerenciador-de-tarefas
    ```

2.  **Crie o arquivo de variáveis de ambiente:**
    Na raiz do projeto, crie um arquivo chamado `.env` e preencha com as suas credenciais do banco de dados e um segredo para o JWT.

    ```env
    # Configurações do Banco de Dados
    DB_URL=jdbc:postgresql://postgres:5432/gerenciador_tarefas
    DB_USER=seu_usuario
    DB_PASSWORD=sua_senha_forte

    # Chave secreta para a assinatura do JWT
    JWT_SECRET=coloque_aqui_um_segredo_longo_e_aleatorio
    ```
    > **Nota:** No `docker-compose.yml`, o serviço do banco de dados já está configurado como `postgres`. Mantenha o `DB_URL` como no exemplo acima se for usar Docker.

### Executando com Docker (Recomendado)

A maneira mais simples de rodar a aplicação, pois gerencia tanto a API quanto o banco de dados.

```bash
docker-compose up --build
