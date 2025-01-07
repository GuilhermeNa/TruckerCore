# Modulo de integração com Firebase

1. [ Visão Geral ](#1-visão-geral)
2. [ Estrutura ](#2-estrutura)
3. [ Erros ](#3-erros)
4. [ Interfaces ](#4-interfaces)
5. [ Fluxo de dados ](#5-fluxo-de-dados)

### 1. **Visão Geral**

Este módulo fornece a integração com o Firestore, utilizando o Firebase SDK para realizar operações
de banco de dados. Ele abrange funcionalidades como a criação, leitura, atualização e exclusão 
(CRUD)de documentos no Firestore, bem como o processamento de dados usando DTOs
(Data Transfer Objects).

### 2. **Estrutura**

database/
│
└── firebase/            
    ├── errors/
    │── implementations/
    │── interfaces/
    │── util/
    └── README

### 3. **Erros**

# `FirebaseConversionException`
A classe `FirebaseConversionException` é uma exceção personalizada que indica erros durante a 
conversão de dados entre documentos do Firestore e os DTOs. Isso pode ocorrer quando há falha 
na conversão dos dados de Firestore para os objetos do sistema.

### 4. **Interfaces**

# `FirebaseConverter<T>`
A interface `FirebaseConverter` é responsável por converter os dados entre os documentos do 
Firestore e os DTOs utilizados no sistema.

# `FirebaseQueryBuilder`
A interface `FirebaseQueryBuilder` é responsável pela construção de consultas (queries) e 
referências de documentos no Firestore.

# `FirebaseRepository<T : Dto>`
A interface `FirebaseRepository` oferece métodos para realizar operações de CRUD
(Create, Read, Update, Delete) em documentos no Firestore.

### 5. **Fluxo de Dados**

As operações no Firestore retornam um tipo `Response`, que pode ser:
- **`Response.Success<T>`**: Quando a operação é bem-sucedida.
- **`Response.Error`**: Quando ocorre um erro durante a operação.
- **`Response.Empty`**: Quando o dado não é encontrado ou não há resultado para a operação.