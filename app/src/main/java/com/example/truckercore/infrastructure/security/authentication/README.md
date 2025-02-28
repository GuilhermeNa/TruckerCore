# Módulo de Autenticação e Criação de Acesso

1. [Visão Geral](#1-visão-geral)
2. [Estrutura](#2-estrutura)
3. [Classes](#3-classes)
4. [Fluxo de Dados](#4-fluxo-de-dados)
5. [Erros](#5-erros)

### 1. **Visão Geral**

Este módulo é responsável pela autenticação de usuários e criação de acessos no sistema.
Ele envolve processos como o login, logout e a criação de novos acessos, utilizando o Firebase
como sistema de autenticação e banco de dados. O módulo também gerencia a validação de dados dos
usuários e integra a criação de acessos com a configuração de dados no Firestore.

### 2. **Estrutura**

`authentication/ 
│ 
└── auth/
    ├── entity/
    ├── errors/
    ├── expressions/ 
    ├── service/
    ├── use_cases/
    └── README`

### 3. **Classes**

- **Credentials**: Representa as credenciais de autenticação do usuário, incluindo email e senha.
  Realiza a validação dos dados antes de permitir a autenticação.

- **NewAccessRequirements**: Armazena os dados necessários para a criação de um novo acesso ao
  sistema, como nome, sobrenome, email e categoria do usuário (ex: motorista ou administrador).

- **LoggedUserDetails**: Representa os detalhes completos de um usuário logado, incluindo os dados
  do usuário autenticado e suas informações associadas, como os dados pessoais.

- **AuthService**: Interface que define os métodos para autenticação de usuários e criação de
  acessos no sistema.

- **CreateNewSystemAccessUseCase**: Interface que define o caso de uso para criar um novo acesso no
  sistema, gerenciando os dados no Firebase.

- **GetLoggedUserDetailsUseCase**: Define o caso de uso para buscar os detalhes completos do usuário
  logado. A implementação recupera os dados do usuário autenticado no Firebase, e em seguida, busca
  e retorna suas informações associadas, como dados pessoais e arquivos relacionados.

### 4. **Fluxo de Dados**

As operações de autenticação e criação de acessos seguem o seguinte fluxo:

- **Response.Success<T>**: Retornado quando a operação (como login ou criação de acesso) é
  bem-sucedida.

- **Response.Error**: Retornado quando ocorre uma falha na operação, como credenciais inválidas ou
  falha na criação de um novo acesso.

- **`Fluxo de Autenticação de Credenciais`**

1. O usuário fornece suas credenciais (email e senha), que são passadas para a classe `Credentials`.
2. As credenciais são validadas e formatadas antes de serem enviadas para o `AuthService`.
3. Se a autenticação for bem-sucedida, um token de autenticação é retornado através de um
   `Flow<Response<String>>`.

- **`Fluxo de Criação de Novo Acesso`**

1. O objeto `NewAccessRequirements` é preenchido com os dados necessários para criar um novo acesso,
   incluindo UID, nome, sobrenome, email e a categoria do usuário.
2. O caso de uso `CreateNewSystemAccessUseCaseImpl` é chamado para gerenciar o processo de criação
   do novo acesso.
3. O sistema valida os dados e cria o novo acesso no Firebase, retornando uma resposta através de
   `Flow<Response<Unit>>`.
4. Caso algum dado esteja incorreto, a operação falha e uma exceção é lançada.

- **`Fluxo de Logout`**

1. O método `signOut()` do `AuthServiceImpl` é chamado para realizar o logout do usuário.
2. O sistema encerra a sessão atual e limpa qualquer dado de autenticação armazenado.

- **`Fluxo de busca por dados do usuário logado`**

1. O identificador único do usuário no Firebase (firebaseUid) é passado para o caso de uso
   GetLoggedUserDetailsUseCase.
2. O caso de uso chama getUser.execute(firebaseUid) para buscar os dados do usuário logado.
3. Se os dados do usuário forem encontrados, o sistema chama getPersonDetails.execute(user) para
4. Quando os dados do usuário e os dados associados são encontrados, o fluxo retorna um
   Response.Success<LoggedUserDetails>, contendo as informações do usuário logado, juntamente
   com os detalhes de sua pessoa associada.
5. Caso algum dado não seja encontrado, uma exceção ObjectNotFoundException é lançada.

### 5. **Erros**

- **NullFirebaseUseException**: Lançado quando ocorre uma falha devido à falta do identificador
  único do usuário (firebaseUid) no processo de autenticação ou busca de dados.