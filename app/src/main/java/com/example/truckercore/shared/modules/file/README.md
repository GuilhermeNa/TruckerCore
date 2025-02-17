# Módulo de Gestão de Arquivos de Armazenamento
  
## Índice
    
1. [Visão Geral](#1-visão-geral)
2. [Funcionalidades](#2-funcionalidades)
3. [Fluxo de Dados](#3-fluxo-de-dados)
4. [Estrutura](#4-estrutura)
    
---
    
## 1. **Visão Geral**
    
Este módulo gerencia o armazenamento de arquivos em Firebase Storage. Ele facilita a interação com
os arquivos armazenados, incluindo operações como criação, leitura, atualização, exclusão e
verificação de existência. O módulo também oferece suporte para mapear os dados entre a camada de
persistência e os objetos de transferência de dados (DTOs).

### Modelo de Dados
    
- **StorageFile**: Representa um arquivo armazenado no Firebase Storage. Inclui metadados como o
  `parentId`, `url` do arquivo, e o status de atualização (`isUpdating`).
- **StorageFileDto**: Um DTO (Data Transfer Object) usado para transferir dados entre o banco de
  dados e a aplicação. Ele contém campos similares aos do `StorageFile`, mas com tipos de dados
  adaptados para transporte.

### Exceções Personalizadas

- **StorageFileValidationException**: Lançada quando ocorre uma falha de validação em um arquivo de
  armazenamento. Essa exceção garante que todos os dados necessários estejam corretos antes da
  persistência.
- **StorageFileMappingException**: Lançada quando ocorre um erro ao mapear os dados entre os objetos
  `StorageFile` e `StorageFileDto`.

### Mapeadores

O módulo utiliza um padrão de mapeamento para transformar dados entre entidades e DTOs. A classe
`StorageFileMapper` realiza o mapeamento entre `StorageFile` e `StorageFileDto`, cuidando das
conversões necessárias para garantir a integridade dos dados.

---

## 2. **Funcionalidades**

- **CreateStorageFileUseCase**: Cria um novo arquivo de armazenamento no sistema. Este caso de uso
  valida os dados do arquivo e realiza a persistência no Firebase Storage.
- **UpdateStorageFileUseCase**: Atualiza um arquivo existente no sistema. Permite modificar os
  metadados do arquivo, incluindo a URL e o status de atualização.
- **DeleteStorageFileUseCase**: Exclui um arquivo do sistema, removendo-o permanentemente do
  armazenamento.
- **GetStorageFileUseCase**: Recupera um arquivo de armazenamento pelo seu ID ou por um conjunto de
  filtros. Permite buscar detalhes de um arquivo específico ou uma lista.
- **GetStorageFileByParentIdUseCase**: Recupera arquivos de armazenamento relacionados a um ID de "
  pai" (parentId). Este caso de uso permite buscar múltiplos arquivos associados a um objeto
  principal.
- **CheckStorageFileExistenceUseCase**: Verifica se um arquivo de armazenamento existe no sistema,
  retornando um valor booleano indicando sua presença ou ausência.

---

## 3. **Fluxo de Dados**

As operações do módulo retornam um tipo de `Response`, que pode ser:

- **Response.Success<T>**: Quando a operação é bem-sucedida e retorna dados (como um ID ou um
  objeto).
- **Response.Error**: Quando ocorre um erro durante a operação, como falhas na persistência ou
  validação.
- **Response.Empty**: Quando não há dados ou resultados para a operação solicitada (como quando uma
  busca não retorna arquivos).

---

## 4. **Estrutura**

```text
modules/
│
└── storage_file/
    ├── dto/
    ├── entity/
    ├── mappers/
    ├── repository/
    ├── use_cases/
    ├── validator/
    └── README.md