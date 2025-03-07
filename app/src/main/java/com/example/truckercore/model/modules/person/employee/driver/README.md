# Módulo de Gestão de Drivers

Este módulo gerencia a criação, atualização, validação e exclusão de **Drivers** no sistema. A
classe `Driver` representa um funcionário especializado como motorista, com informações pessoais e
de status relacionadas ao cargo.

## Índice

1. [Visão Geral](#1-visão-geral)
2. [Funcionalidades](#2-funcionalidades)
3. [Fluxo de Dados](#3-fluxo-de-dados)
4. [Estrutura](#4-estrutura)

---

## 1. **Visão Geral**

O módulo de gestão de **Drivers** oferece mecanismos para gerenciar os dados dos motoristas do
sistema, integrando-os ao sistema central chamado **BusinessCentral**. Além disso, oferece
funcionalidades para validar, atualizar e excluir registros de motoristas.

### Modelo de Dados

- **Driver**: Representa um motorista do sistema, com informações sobre o status do funcionário,
  dados pessoais e de acesso.
- **DriverDto**: Um DTO (Data Transfer Object) utilizado para transferir dados entre a camada de
  persistência e a aplicação.

### Mapeadores

O módulo utiliza um padrão de mapeamento para converter entre as representações de dados de **Driver
** no sistema, como `Driver` (entidade) e `DriverDto` (Data Transfer Object). O processo de
mapeamento garante a manipulação correta dos dados entre as camadas do sistema.

### Validadores

Os validadores asseguram que os dados de entrada (seja de um `DriverDto` ou uma entidade `Driver`)
atendem às regras e critérios do sistema. Cada tipo de dado possui uma lógica de validação
específica para garantir sua integridade antes de ser persistido ou manipulado.

---

## 2. **Funcionalidades**

- **CreateDriverUseCase**: Cria novos motoristas no sistema. Este caso de uso valida os dados de
  entrada e realiza a persistência no banco de dados. Ele também verifica se todos os campos
  obrigatórios estão preenchidos corretamente antes de concluir a criação.

- **UpdateDriverUseCase**: Atualiza as informações de um motorista existente. Permite a modificação
  dos dados de um motorista, incluindo o status do empregado e as permissões. A lógica de
  atualização verifica se o usuário solicitante tem as permissões necessárias.

- **DeleteDriverUseCase**: Exclui um motorista do sistema. Verifica se o usuário tem permissões
  adequadas para realizar a operação e realiza a remoção do motorista na base de dados.

- **GetDriverUseCase**: Recupera um motorista existente pelo seu ID. Este caso de uso permite buscar
  informações de um motorista com base em um identificador único (ID), garantindo que apenas
  usuários autorizados possam acessar os dados.

- **CheckDriverExistenceUseCase**: Verifica se um motorista existe no sistema. Retorna um valor
  booleano indicando a presença do motorista na base de dados.

---

## 3. **Fluxo de Dados**

As operações no sistema retornam um tipo de `Response`, que pode ser:

- **Response.Success<T>**: Quando a operação é bem-sucedida e retorna dados (por exemplo, ID ou
  objeto).
- **Response.Error**: Quando ocorre um erro na operação, como falhas na persistência ou validação.
- **Response.Empty**: Quando não há dados ou resultados para a operação solicitada (por exemplo,
  quando uma busca não retorna resultados).

---

## 4. **Estrutura**

```text
modules/
│
└── driver/
    ├── dto/
    ├── entity/
    ├── errors/
    ├── mappers/
    ├── repository/
    ├── use_cases/
    ├── validator/
    └── README.md