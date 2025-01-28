# Módulo de Gestão de Dados Pessoais

## Índice

1. [Visão Geral](#1-visão-geral)
2. [Funcionalidades](#2-funcionalidades)
3. [Fluxo de Dados](#3-fluxo-de-dados)
4. [Estrutura](#4-estrutura)

---

## 1. **Visão Geral**

Este módulo gerencia a criação, atualização, validação e exclusão de **Dados Pessoais** no sistema.
Ele lida com documentos pessoais, como CPF, RG, carteira de motorista, e outros documentos
semelhantes, proporcionando um gerenciamento eficaz dessas informações.

A classe `PersonalData` representa dados pessoais de um indivíduo, como documentos de identidade,
com informações detalhadas sobre os documentos e suas datas importantes.

### Modelo de Dados

- **PersonalData**: Representa os dados pessoais do usuário, como documentos de identidade,
  incluindo informações sobre o documento, como nome, número, datas de emissão e validade.
- **PersonalDataDto**: Um DTO (Data Transfer Object) utilizado para transferir dados entre a camada
  de persistência e a aplicação.

### Exceções Personalizadas

- **PersonalDataMappingException**: Lançada quando ocorre uma falha ao mapear entre o objeto
  `PersonalData` e `PersonalDataDto`.
- **PersonalDataValidationException**: Lançada quando há falhas nas validações aplicadas aos dados
  de `PersonalData`.

### Mapeadores

O módulo utiliza um padrão de mapeamento para converter entre as representações de dados de
`PersonalData` no sistema, como `PersonalData` (entidade) e `PersonalDataDto` (DTO). O processo de
mapeamento garante que os dados sejam manipulados corretamente entre as camadas do sistema.

### Validadores

Os validadores asseguram que os dados de entrada atendam às regras e critérios do sistema. Eles
verificam a integridade dos dados antes de serem persistidos ou manipulados, assegurando que
informações como CPF, RG, e datas importantes sejam válidas.

---

## 2. **Funcionalidades**

- **CreatePersonalDataUseCase**: Cria novos dados pessoais no sistema. Este caso de uso valida os
  dados de entrada e realiza a persistência no banco de dados, verificando se todos os campos
  obrigatórios estão preenchidos corretamente.

- **UpdatePersonalDataUseCase**: Atualiza as informações de um dado pessoal existente. Permite a
  modificação dos dados, incluindo o número do documento e as datas de emissão e validade. A lógica
  de atualização verifica se o usuário solicitante tem as permissões necessárias.

- **DeletePersonalDataUseCase**: Exclui um dado pessoal do sistema. Verifica se o usuário tem
  permissões adequadas para realizar a operação e remove o dado na base de dados.

- **GetPersonalDataByIdUseCase**: Recupera um dado pessoal existente pelo seu ID. Permite buscar
  informações sobre um dado pessoal com base em um identificador único (ID), garantindo que apenas
  usuários autorizados possam acessar os dados.

- **GetPersonalDataByParentIdUseCase**: Recupera os dados pessoais relacionados a um ID de pai (
  parentId). Este caso de uso permite buscar múltiplos registros de dados pessoais associados a uma
  entidade principal.

- **CheckPersonalDataExistenceUseCase**: Verifica se um dado pessoal existe no sistema. Retorna um
  valor booleano indicando a presença do dado pessoal na base de dados.

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
└── personal_data/
    ├── dto/
    ├── entity/
    ├── errors/
    ├── mappers/
    ├── repository/
    ├── use_cases/
    ├── validator/
    └── README.md