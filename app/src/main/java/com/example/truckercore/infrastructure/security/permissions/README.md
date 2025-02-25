# Módulo de Permissões de Acesso

1. [Visão Geral](#1-visão-geral)
2. [Estrutura](#2-estrutura)
3. [Classes](#3-classes)
4. [Fluxo de Dados](#4-fluxo-de-dados)

### 1. **Visão Geral**

Este módulo é responsável pela gestão de permissões e controle de acesso dentro do sistema. Ele
define os diferentes níveis de acesso, as permissões associadas a usuários e serviços, e verifica se
um usuário tem autorização para realizar determinadas ações. O módulo utiliza as permissões para
garantir que as operações sejam realizadas de forma segura, de acordo com as credenciais e funções
do usuário.

### 2. **Estrutura**

`permissions/ 
 │ 
 │── enums/
 │── service/
 │── errors/
 └── README.md`

### 3. **Classes**

- **Level**: Representa os diferentes níveis de privilégio no sistema, com funções e permissões
  associadas a cada nível de acesso (ex: MASTER, MANAGER, MODERATOR, DRIVER). Esta enumeração define
  o grau de controle do usuário sobre o sistema.

- **Permission**: Define um conjunto de permissões que podem ser atribuídas a usuários ou serviços,
  como criar, atualizar, excluir ou visualizar diferentes entidades, como licenças, trailers,
  usuários, etc.

- **UnauthorizedAccessException**: Exceção lançada quando um usuário ou serviço tenta realizar uma
  ação sem a permissão necessária. Ela é útil para implementar controle de acesso no sistema.

- **PermissionService**: Interface que define os métodos para verificar se um usuário possui a
  permissão necessária para executar uma ação específica.

### 4. **Fluxo de Dados**

As operações de controle de permissões seguem o seguinte fluxo:

- **`Response.Success<T>`**: Retornado quando a verificação de permissão é bem-sucedida, ou seja, o
  usuário possui a permissão necessária para realizar a ação.

#### Fluxo de Verificação de Permissão

1. O sistema verifica se o usuário possui o nível de acesso adequado através da enumeração `Level`.
2. As permissões do usuário são comparadas com as permissões necessárias para realizar a ação.
3. Se o usuário tiver a permissão, a ação é permitida e a resposta `Response.Success` é retornada.
4. Caso contrário, uma exceção `UnauthorizedAccessException` é lançada para sinalizar que o usuário
   não tem permissão suficiente.

