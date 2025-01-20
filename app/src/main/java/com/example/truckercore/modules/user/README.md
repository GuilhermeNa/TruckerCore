# Módulo de Gestão de Usuários

Este módulo gerencia a criação, atualização, validação e exclusão de usuários no sistema. A classe
`User` representa qualquer pessoa com acesso ao sistema. Atualmente, os usuários no sistema são
todos funcionários, e um **usuário master** é criado no primeiro acesso para administrar as
permissões dos demais usuários.

## Índice

1. [Visão Geral](#1-visão-geral)
2. [Funcionalidades](#2-funcionalidades)
3. [Fluxo_de_dados](#3-fluxo-de-dados) 
4. [Estrutura](#4-estrutura)

---

## 1. **Visão Geral**

O módulo de gestão de usuários é responsável por fornecer os mecanismos para manipular as
informações de usuários no sistema. Além disso, ele integra os usuários com um sistema central
chamado **BusinessCentral**. Cada **usuário master** criado tem permissões totais para conceder ou
revogar permissões de outros usuários. O **usuário master** é criado automaticamente no primeiro
acesso de qualquer novo usuário.

### Modelo de Dados

- **User**: Representa um usuário do sistema. A classe `User` contém informações sobre o nível de
  acesso, permissões e dados pessoais, entre outros.
- **UserDto**: Um DTO (Data Transfer Object) utilizado para transferir dados entre a camada de
  persistência e a aplicação.

### Exceções Personalizadas

- **UserMappingException**: Lançada quando ocorre uma falha na conversão entre o objeto `User` e
  `UserDto`.
- **UserValidationException**: Lançada quando falham as validações aplicadas aos dados do `User`.

### Mapeadores

O módulo utiliza um padrão de mapeamento para converter entre as representações de dados de usuários
no sistema, como `User` (entidade) e `UserDto` (Data Transfer Object). Este processo é fundamental
para garantir que os dados sejam corretamente manipulados e transportados entre as camadas do
sistema.

### Validadores

Os validadores garantem que os dados de entrada, seja de um `UserDto` ou de uma entidade `User`,
atendam a todas as regras e critérios do sistema. Cada tipo de dado possui uma lógica de validação
específica para garantir sua integridade antes de ser persistido ou manipulado.

---

## 2. **Funcionalidades**

- **CreateUserUseCase**: Cria novos usuários no sistema. Este caso de uso trata da lógica necessária
  para criar um usuário, incluindo a validação dos dados e a persistência no banco de dados. O
  sistema garante que todos os campos obrigatórios sejam preenchidos corretamente antes de realizar
  a criação.

- **UpdateUserUseCase**: Atualiza as informações de um usuário existente. Este caso de uso permite
  modificar os dados de um usuário, incluindo permissões e nível de acesso. Antes da atualização,
  são realizadas verificações de permissões para garantir que o usuário que solicita a atualização
  tenha autorização para modificar as informações.

- **DeleteUserUseCase**: Deleta um usuário do sistema. Este caso de uso trata da lógica necessária
  para remover um usuário da base de dados. Além disso, ele valida se o usuário que solicita a
  exclusão tem permissões suficientes para realizar essa operação.

- **GetUserByIdUseCase**: Recupera um usuário existente através de seu ID. Este caso de uso permite
  que as informações de um usuário sejam recuperadas com base em um identificador único (ID). Ele
  realiza verificações de permissões antes de retornar os dados, garantindo que apenas usuários
  autorizados possam acessar as informações.

- **CreateMasterUserUseCase**: Cria o primeiro usuário master de um novo sistema. Este caso de uso é
  executado quando um novo sistema é iniciado, criando o primeiro usuário com permissões totais (
  usuário master). O usuário master é responsável por conceder permissões aos outros usuários do
  sistema.

- **CheckUserExistenceUseCase**: Verifica a existência de um usuário por seu ID. Este caso de uso
  permite verificar se um usuário já existe no sistema antes de realizar operações como atualização
  ou exclusão. Ele retorna um valor booleano indicando se o usuário está presente ou não na base de
  dados.

---

## 3. **Fluxo de Dados**

As operações no sistema retornam um tipo Response, que pode ser:

Response.Success<T>: Quando a operação é bem-sucedida e retorna dados (por exemplo, ID ou objeto).
Response.Error: Quando ocorre um erro na operação, como falhas na persistência ou validação.
Response.Empty: Quando não há dados ou resultados para a operação solicitada (por exemplo, quando
uma busca não retorna resultados).

## 4. **Estrutura**

```text
modules/
│
└── user/
    ├── dto/
    ├── entity/
    ├── errors/
    ├── mappers/
    ├── repository/
    ├── use_cases/
    ├── validator/
    └── README.md