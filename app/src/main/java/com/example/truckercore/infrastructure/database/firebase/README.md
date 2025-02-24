# Modulo de integração com Firebase

1. [ Visão Geral ](#1-visão-geral)
2. [ Estrutura ](#2-estrutura)
3. [ Erros ](#3-erros)
4. [ Classes ](#4-classes)
5. [ Fluxo de dados ](#5-fluxo-de-dados)

### 1. **Visão Geral**

Este módulo fornece a integração com o Firestore, utilizando o Firebase SDK para realizar operações
de banco de dados e autenticação. Ele abrange funcionalidades como a autenticação, criação, leitura,
atualização e exclusão(CRUD)de documentos no Firestore, bem como o processamento de dados usando DTOs
(Data Transfer Objects).

### 2. **Estrutura**

`database/
│
└── firebase/            
    ├── errors/
    │── repository/
    │── util/
    └── README`

### 3. **Erros**

`FirebaseConversionException`: Indica erros durante a conversão de dados entre documentos do
Firestore e os DTOs. Isso pode ocorrer quando há falha na conversão dos dados de Firestore para os
objetos do sistema.

`FirebaseRequestException`: Indica erros que ocorrem durante a criação ou processamento de uma
requisição do Firestore, especificamente quando os parâmetros esperados não são atendidos ou quando
ocorre uma falha no manuseio da coleção e parâmetros fornecidos.

`UnsuccessfulTaskException`: Indica que uma tarefa do Firestore falhou ao ser executada. Essa
exceção é gerada quando uma operação, como uma consulta ou escrita no Firestore, não consegue ser
concluída com sucesso.

### 4. **Classes**

`FirebaseRepository`: oferece métodos para realizar operações de CRUD
(Create, Read, Update, Delete) em documentos no Firestore.

`FirebaseAuthRepository`: oferece métodos para realizar operações de autenticação,
login, logout e busca por usuários no Firebase.

`FirebaseConverter`: é responsável por converter os dados entre os documentos do
Firestore e os DTOs utilizados no sistema.

`FirebaseQueryBuilder`: é responsável pela construção de consultas (queries) e
referências de documentos no Firestore.

`FirebaseRequest`: é responsável por armazenar os dados das requisições de dados que serão 
realizadas junto ao firebase. 

### 5. **Fluxo de Dados**

As operações no Firestore retornam um tipo `Response`, que pode ser:

`Response.Success<T>`: Quando a operação é bem-sucedida.
`Response.Empty`: Quando o dado não é encontrado ou não há resultado para a operação.