# Módulo de Trailer

1. [Visão Geral](#1-visão-geral)
2. [Estrutura](#2-estrutura)
3. [Classes](#3-classes)
4. [Fluxo de Dados](#4-fluxo-de-dados)

### 1. **Visão Geral**

Este módulo é responsável pela gestão dos trailers no sistema, englobando funcionalidades como
cadastro, atualização, exclusão, e recuperação de trailers. Ele faz uso do Firebase como banco de
dados para persistência de informações e validação de dados.

### 2. **Estrutura**

`trailer/
 │
 ├── aggregations/
 ├── dto/ 
 ├── entity/
 ├── enums/
 ├── mapper/
 ├── repository/
 ├── service/
 ├── use_cases/
 ├── validation/
 └── README`

### 3. **Classes**

- **Trailer**: Representa um trailer no sistema, contendo informações como placa, cor, marca,
  categoria, entre outros. Esta classe é usada para persistir os dados de trailers e realizar
  operações CRUD.

- **TrailerDto**: Classe de transferência de dados, que transforma a entidade Trailer em um formato
  adequado para comunicação entre diferentes camadas do sistema.

- **TrailerBrand**: Enum que representa as marcas de trailers disponíveis no sistema, como
  LIBRELATO, RANDON, entre outras.

- **TrailerCategory**: Enum que representa as categorias dos trailers, como 3 eixos, 4 eixos, etc.

- **TrailerRepository**: Interface responsável pelas operações de persistência de trailers, como
  salvar, atualizar, excluir e buscar trailers no banco de dados.

- **TrailerService**: Interface que define os métodos de serviço para manipulação dos trailers,
  incluindo operações de busca e agregação.

- **CreateTrailerUseCase**: Caso de uso que lida com a criação de trailers no sistema.

- **UpdateTrailerUseCase**: Caso de uso que permite a atualização de trailers existentes.

- **DeleteTrailerUseCase**: Caso de uso que lida com a exclusão de trailers.

- **GetTrailerUseCase**: Caso de uso para recuperar trailers a partir de parâmetros específicos.

- **AggregateTrailerWithDetailsUseCase**: Caso de uso que agrega trailers com detalhes adicionais
  como licenciamento, condições de uso, etc.

- **TrailerValidationStrategy**: Implementa as regras de validação dos trailers, como a verificação
  de campos obrigatórios e validação de regras de negócios.

### 4. **Fluxo de Dados**

As operações relacionadas a trailers seguem o seguinte fluxo:

- **`Response.Success<T>`**: Retornado quando a operação (como criação ou atualização de trailer) é
  bem-sucedida.

- **`Response.Error`**: Retornado quando ocorre uma falha na operação, como dados inválidos ou erro
  na persistência.

#### Fluxo de Criação de Trailer

1. O usuário fornece os dados do trailer (placa, cor, marca, categoria, etc.), que são passados para
   a classe `Trailer`.
2. A classe `CreateTrailerUseCase` valida os dados do trailer utilizando a
   `TrailerValidationStrategy`.
3. Se os dados forem válidos, o trailer é salvo no Firebase através do `TrailerRepository`.
4. O sistema retorna uma resposta bem-sucedida, encapsulada em um `Flow<Response<Trailer>>`.

#### Fluxo de Atualização de Trailer

1. O usuário fornece os novos dados para o trailer a ser atualizado.
2. A classe `UpdateTrailerUseCase` valida e atualiza os dados no banco de dados Firebase.
3. O sistema retorna uma resposta com sucesso ou erro, encapsulada em um `Flow<Response<Trailer>>`.

#### Fluxo de Exclusão de Trailer

1. O usuário fornece o ID do trailer a ser excluído.
2. O caso de uso `DeleteTrailerUseCase` é chamado para excluir o trailer do banco de dados.
3. A operação é confirmada com uma resposta `Flow<Response<Unit>>`.

#### Fluxo de Recuperação de Trailer

1. O usuário fornece parâmetros (como placa ou ID) para buscar um trailer.
2. O caso de uso `GetTrailerUseCase` é chamado para recuperar o trailer com os dados fornecidos.
3. O sistema retorna os dados do trailer ou uma mensagem de erro, encapsulada em um
   `Flow<Response<Trailer>>`.

#### Fluxo de Agregação de Trailer com Detalhes

1. O usuário solicita informações detalhadas de um trailer.
2. O caso de uso `AggregateTrailerWithDetailsUseCase` é chamado para recuperar o trailer junto com
   seus detalhes adicionais.
3. O sistema retorna a resposta com os dados agregados ou um erro em caso de falha.

---