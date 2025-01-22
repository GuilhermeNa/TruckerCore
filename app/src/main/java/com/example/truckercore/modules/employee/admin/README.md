# Módulo de Gestão de Administradores
  
Este módulo gerencia a criação, atualização, validação e exclusão de **Administradores** no sistema.
A classe `Admin` representa um funcionário especializado no gerenciamento de usuários, com
informações pessoais e de status relacionadas ao cargo de administrador.
  
## Índice
  
1. [Visão Geral](#1-visão-geral)
2. [Funcionalidades](#2-funcionalidades)
3. [Fluxo de Dados](#3-fluxo-de-dados)
4. [Estrutura](#4-estrutura)
  
---
  
## 1. **Visão Geral**
  
O módulo de gestão de **Administradores** oferece mecanismos para gerenciar os dados dos
administradores do sistema, integrando-os ao sistema central chamado **BusinessCentral**. Além
disso, oferece funcionalidades para validar, atualizar e excluir registros de administradores.
  
### Modelo de Dados
  
- **Admin**: Representa um administrador do sistema, com informações sobre o status do empregado,
  dados pessoais, informações de acesso e status de funcionário.
- **AdminDto**: Um DTO (Data Transfer Object) utilizado para transferir dados entre a camada de
  persistência e a aplicação.
  
### Exceções Personalizadas
  
- **AdminMappingException**: Lançada quando ocorre uma falha ao mapear entre o objeto `Admin` e
  `AdminDto`.
- **AdminValidationException**: Lançada quando há falhas nas validações aplicadas aos dados do
  `Admin`.
  
### Mapeadores
  
O módulo utiliza um padrão de mapeamento para converter entre as representações de dados de **Admin
** no sistema, como `Admin` (entidade) e `AdminDto` (Data Transfer Object). O processo de mapeamento
garante a manipulação correta dos dados entre as camadas do sistema.
  
### Validadores
  
Os validadores asseguram que os dados de entrada (seja de um `AdminDto` ou uma entidade `Admin`)
atendem às regras e critérios do sistema. Cada tipo de dado possui uma lógica de validação
específica para garantir sua integridade antes de ser persistido ou manipulado.
  
---
  
## 2. **Funcionalidades**
  
- **CreateAdminUseCase**: Cria novos administradores no sistema. Este caso de uso valida os dados de
  entrada e realiza a persistência no banco de dados. Ele também verifica se todos os campos
  obrigatórios estão preenchidos corretamente antes de concluir a criação.
  
- **UpdateAdminUseCase**: Atualiza as informações de um administrador existente. Permite a
  modificação dos dados de um administrador, incluindo o status do empregado e as permissões. A
  lógica de atualização verifica se o usuário solicitante tem as permissões necessárias.
  
- **DeleteAdminUseCase**: Exclui um administrador do sistema. Verifica se o usuário tem permissões
  adequadas para realizar a operação e realiza a remoção do administrador na base de dados.
  
- **GetAdminUseCase**: Recupera um administrador existente pelo seu ID. Este caso de uso permite
  buscar informações de um administrador com base em um identificador único (ID), garantindo que
  apenas usuários autorizados possam acessar os dados.
  
- **CheckAdminExistenceUseCase**: Verifica se um administrador existe no sistema. Retorna um valor
  booleano indicando a presença do administrador na base de dados.
  
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
└── admin/
    ├── dto/
    ├── entity/
    ├── errors/
    ├── mappers/
    ├── repository/
    ├── use_cases/
    ├── validator/
    └── README.md