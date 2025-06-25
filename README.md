# AgendaFit - Documentação Técnica

O AgendaFit é um sistema de gerenciamento de treinos e exercícios de academia, projetado para ajudar os usuários a organizar e acompanhar suas rotinas de condicionamento físico.

## Arquitetura

O sistema é construído em uma arquitetura cliente-servidor de três camadas, composta por:

1.  **Backend (AgendaFit-Servidor):** O núcleo do sistema, que lida com a lógica de negócios, persistência de dados e comunicação com os clientes.
2.  **Cliente Desktop (AgendaFit-Cliente):** Uma interface de desktop para administradores, que permite o gerenciamento completo de usuários, exercícios e treinos.
3.  **Cliente Móvel (AgendaFit-Mobile):** Uma aplicação Android para o usuário final, permitindo a visualização de treinos e o acompanhamento de seu progresso.

---

## AgendaFit-Servidor (Backend)

### Tecnologias e Implementação

* **Linguagem:** Java.
* **Comunicação:** Utiliza Sockets para a comunicação com os clientes (Desktop e Móvel). A classe `AgendaFitServer` inicia um `ServerSocket` na porta `12345`.
* **Multithreading:** Para cada cliente que se conecta, uma nova thread (`TrataClienteController`) é instanciada para lidar com as requisições, permitindo que o servidor atenda múltiplos clientes simultaneamente.
* **Serialização de Objetos:** A comunicação entre cliente e servidor é feita através da serialização de objetos Java (`ObjectInputStream` e `ObjectOutputStream`), o que facilita o tráfego de dados complexos como `Usuario`, `Treino` e `Exercicio`.
* **Persistência de Dados:**
    * **Banco de Dados:** MySQL. A conexão é gerenciada pela classe `Conector`.
    * **Padrão de Acesso a Dados (DAO):** A interação com o banco de dados é abstraída através de classes DAO, como `UsuarioDao`, `TreinoDao` e `ExercicioDao`. Isso separa a lógica de negócios das operações de banco de dados.

### Estrutura do Banco de Dados (inferida dos DAOs)

* `usuarios`: Armazena os dados dos usuários (nome, login, senha, etc.).
* `exercicios`: Contém a lista de todos os exercícios disponíveis.
* `treinos`: Armazena os treinos criados, com suas descrições, datas e tipos.
* `treinoexercicio`: Tabela de junção que mapeia a relação N:N entre treinos e exercícios.
* `treinousuario`: Tabela de junção que mapeia a relação N:N entre usuários e treinos.

---

## AgendaFit-Cliente (Desktop)

A aplicação de desktop serve como uma ferramenta de administração para o sistema.

### Tecnologias e Implementação

* **Linguagem:** Java.
* **Interface Gráfica:** Construída com a biblioteca **Swing**.
* **Comunicação:** Assim como o servidor, utiliza Sockets para a comunicação. A classe `ConexaoController` gerencia a conexão, enviando e recebendo objetos serializados.
* **Visualização de Dados:** Utiliza o `AbstractTableModel` (ex: `TabelaUsuarioModel`, `TabelaExercicioModel` e `TabelaTreinoModel`) para popular as tabelas (`JTable`) com os dados recebidos do servidor.
* **Segurança:** A senha do usuário é criptografada em MD5 antes de ser enviada para o servidor, utilizando o método `encryptPassword` na classe `Util`.

---

## AgendaFit-Mobile (Android)

A aplicação móvel é a interface do usuário final, focada na visualização e interação com os dados de treino.

### Tecnologias e Implementação

* **Linguagem:** Java.
* **Plataforma:** Android.
* **Comunicação:**
    * O projeto apresenta uma dualidade na comunicação. Existe uma implementação baseada em Sockets no `ConexaoController` (similar à do cliente desktop), que se conecta ao servidor na `10.0.2.2` (endereço do localhost do emulador Android) na porta `12345`.
    * Paralelamente, há uma implementação com a biblioteca **Retrofit** (`RetrofitClient` e `AuthService`), que aponta para um endpoint `http://localhost:8080/api/users/auth/login`. Isso sugere que o projeto pode estar em uma fase de transição para uma arquitetura baseada em API REST, ou que utiliza ambas as abordagens para diferentes funcionalidades.
* **Interface do Usuário:**
    * Layouts definidos em XML (ex: `activity_main.xml`, `content_menu.xml`).
    * Utiliza `RecyclerView` com `CardView` para exibir listas de treinos e exercícios de forma eficiente e visualmente agradável.
    * Componentes customizados, como o `SpinnerMultiSelecionavel`, que permite a seleção de múltiplos exercícios ao cadastrar um treino.
* **Visualização de Dados:**
    * A tela de perfil do usuário (`PerfilActivity`) utiliza a biblioteca **MPAndroidChart** para exibir gráficos de pizza (`PieChart`), mostrando a distribuição de treinos e exercícios por tipo.

 ---
 
## Tecnologias Utilizadas

* **Backend:** Java
* **Banco de Dados:** MySQL
* **Cliente Desktop:** Java Swing
* **Aplicação Móvel:** Android (Java)

## Como Começar

### Pré-requisitos

* Java Development Kit (JDK) instalado
* Servidor de banco de dados MySQL
* Android Studio (para a aplicação móvel)

### Instalação

1.  **Configuração do Backend (Servidor):**
    * Importe o projeto `AgendaFit-Servidor` para sua IDE de preferência.
    * Configure a conexão com o banco de dados no arquivo `AgendaFit-Servidor/src/factory/Conector.java`, atualizando a URL, o nome do banco, o usuário e a senha.
    * Execute a classe `AgendaFitServer` para iniciar o servidor.

2.  **Cliente Desktop:**
    * Importe o projeto `AgendaFit-Cliente` para sua IDE de preferência.
    * Execute a classe `main` para iniciar a aplicação de desktop.

3.  **Aplicação Móvel (Android):**
    * Importe o projeto `AgendaFit-Mobile` para o Android Studio.
    * Compile e execute a aplicação em um emulador ou dispositivo Android.
