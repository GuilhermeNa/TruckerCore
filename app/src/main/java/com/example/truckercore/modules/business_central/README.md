# Modulo de integração com BusinessCentral

1. [ Visão Geral ](#1-visão-geral)
2. [ Estrutura ](#2-estrutura)
3. [ Erros ](#3-erros)
4. [ UseCases ](#4-use-cases)
5. [ Fluxo de Dados ](#5-fluxo-de-dados)

### 1. **Visão Geral**

Este módulo fornece a integração com a entidade BusinessCentral, que é a entidade central do
projeto, responsável por vincular todos os objetos de um determinado usuário, por exemplo,
funcionários, frota, viagens, entre outros.

Padrão DTO (Data Transfer Object) para a manipulação de dados entre a camada de
persistência e a aplicação, garantindo uma comunicação eficiente e estruturada.

Padrão Srategy para a validação de dados de acordo com os critérios e regras estabelecidos 
para este tipo de dado.
    
O módulo oferece funcionalidades como criação, atualização, exclusão e validação de dados.
Também é responsável pelo tratamento de exceções personalizadas e validações específicas
para garantir a integridade e a consistência dos dados.

### 2. **Estrutura**

modules/
│
└── business_central/            
    ├── dto/
    │── entity/
    │── errors/
    │── mapper/
    │── repository
    │── use_cases
    │── validator
    └── README.md

### 3. **Erros**

# `BusinessCentralMappingException`

A classe BusinessCentralMappingException é uma exceção personalizada lançada quando ocorre uma falha
na conversão de dados entre a entidade BusinessCentral e o DTO BusinessCentralDto. Isso pode
acontecer devido a dados inválidos ou erros na configuração de mapeamento.

# `BusinessCentralValidationException`

A classe BusinessCentralValidationException é uma exceção personalizada utilizada para erros de
validação nos dados da entidade BusinessCentral ou BusinessCentralDto. É acionada quando os dados
não atendem aos critérios de validação definidos.

### 4. **Use cases**

As interfaces de casos de uso (por exemplo, CreateBusinessCentralUseCase,
UpdateBusinessCentralUseCase, DeleteBusinessCentralUseCase, GetBusinessCentralByIdUseCase) são
responsáveis por encapsular a lógica de negócios para cada operação. Cada caso de uso é implementado
com a lógica de persistência e validação correspondente.

### 5. **Fluxo de Dados**

As operações no sistema retornam um tipo Response, que pode ser:

Response.Success<T>: Quando a operação é bem-sucedida e retorna dados (por exemplo, ID ou objeto).
Response.Error: Quando ocorre um erro na operação, como falhas na persistência ou validação.
Response.Empty: Quando não há dados ou resultados para a operação solicitada (por exemplo, quando
uma busca não retorna resultados).