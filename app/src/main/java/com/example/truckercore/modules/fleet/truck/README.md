# Módulo de Caminhão

1. [Visão Geral](#1-visão-geral)
2. [Estrutura](#2-estrutura)
3. [Classes](#3-classes)
4. [Fluxo de Dados](#4-fluxo-de-dados)

### 1. **Visão Geral**

Este módulo é responsável pela gestão dos caminhões no sistema, incluindo funcionalidades como
criação, atualização, exclusão, recuperação e validação dos dados de caminhões. Ele também lida com
o gerenciamento de trailers e licenças associadas aos caminhões.

### 2. **Estrutura**

`truck/
│
├── aggregation/
├── dto/
├── entity/
├── enums/
├── mapper/
├── repository/
├── service/
├── use_cases/
├── validator/
└── README.md`

### 3. **Classes**

- **Truck**: Representa um caminhão no sistema, contendo informações como placa, cor, marca, e
  status. Esta classe é usada para persistir os dados de caminhões e realizar operações CRUD.

- **TruckDto**: Classe de transferência de dados, que transforma a entidade Truck em um formato
  adequado para comunicação entre diferentes camadas do sistema.

- **TruckWithDetails**: Representa um caminhão com seus trailers e licenças associadas, oferecendo
  uma visão detalhada do caminhão para o usuário.

- **TruckBrand**: Enum que define as marcas de caminhões disponíveis no sistema, como SCANIA, VOLVO,
  entre outras.

- **TruckRepository**: Interface responsável pelas operações de persistência de caminhões, como
  salvar, atualizar, excluir e buscar caminhões no banco de dados.

- **TruckService**: Interface que define os métodos de serviço para manipulação dos caminhões,
  incluindo operações de busca, agregação de dados e verificação de existência.

- **CreateTruckUseCase**: Caso de uso que lida com a criação de um novo caminhão no sistema,
  incluindo validação de dados e persistência.

- **UpdateTruckUseCase**: Caso de uso que permite a atualização dos dados de caminhões existentes.

- **DeleteTruckUseCase**: Caso de uso que lida com a exclusão de caminhões no sistema.

- **GetTruckUseCase**: Caso de uso para recuperar caminhões a partir de parâmetros específicos, como
  ID ou placa.

- **AggregateTruckWithDetailsUseCase**: Caso de uso que agrega caminhões com detalhes adicionais,
  como trailers e licenças.

- **TruckValidationStrategy**: Implementa as regras de validação dos caminhões, como a verificação
  de campos obrigatórios e a validade dos dados.

### 4. **Fluxo de Dados**

As operações relacionadas a caminhões seguem o seguinte fluxo:

- **`Response.Success<T>`**: Retornado quando a operação (como criação ou atualização de caminhão) é
  bem-sucedida.

- **`Response.Error`**: Retornado quando ocorre uma falha na operação, como dados inválidos ou erro
  na persistência.

#### Fluxo de Criação de Caminhão

1. O usuário fornece os dados do caminhão (placa, cor, marca, etc.), que são passados para a classe
   `Truck`.
2. A classe `CreateTruckUseCase` valida os dados do caminhão utilizando a `TruckValidationStrategy`.
3. Se os dados forem válidos, o caminhão é salvo no banco de dados através do `TruckRepository`.
4. O sistema retorna uma resposta bem-sucedida, encapsulada em um `Flow<Response<Truck>>`.

#### Fluxo de Atualização de Caminhão

1. O usuário fornece os novos dados para o caminhão a ser atualizado.
2. A classe `UpdateTruckUseCase` valida e atualiza os dados no banco de dados.
3. O sistema retorna uma resposta com sucesso ou erro, encapsulada em um `Flow<Response<Truck>>`.

#### Fluxo de Exclusão de Caminhão

1. O usuário fornece o ID do caminhão a ser excluído.
2. O caso de uso `DeleteTruckUseCase` é chamado para excluir o caminhão do banco de dados.
3. A operação é confirmada com uma resposta `Flow<Response<Unit>>`.

#### Fluxo de Recuperação de Caminhão

1. O usuário fornece parâmetros (como ID ou placa) para buscar um caminhão.
2. O caso de uso `GetTruckUseCase` é chamado para recuperar o caminhão com os dados fornecidos.
3. O sistema retorna os dados do caminhão ou uma mensagem de erro, encapsulada em um
   `Flow<Response<Truck>>`.

#### Fluxo de Agregação de Caminhão com Detalhes

1. O usuário solicita informações detalhadas de um caminhão.
2. O caso de uso `AggregateTruckWithDetailsUseCase` é chamado para recuperar o caminhão junto com
   seus trailers e licenças associadas.
3. O sistema retorna a resposta com os dados agregados ou um erro em caso de falha.