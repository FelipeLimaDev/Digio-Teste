# Desafio Android - Banco Digio

![Screenshot do app funcionando](image/screenshot.png)

Este repositório contém um projeto de teste para o **Banco Digio**, implementado com tecnologias e práticas modernas que garantem uma arquitetura robusta, qualidade de código e uma experiência de desenvolvimento eficiente.

---

## Tecnologias e Práticas

### Arquitetura MVI + Clean Architecture
- **Model-View-Intent (MVI)**: Adota o padrão MVI para gerenciar o estado da interface do usuário de forma unidirecional, facilitando a previsibilidade e a manutenção do fluxo de dados.
- **Clean Architecture**: Implementa os princípios da Clean Architecture para separar as camadas de apresentação, domínio e dados, promovendo alta coesão e baixo acoplamento entre os componentes do sistema.

### Catálogo de Versões com Kotlin DSL
- **Kotlin DSL para Version Catalog**: Utiliza Kotlin DSL para gerenciar as versões das dependências do projeto de forma centralizada, facilitando atualizações e garantindo consistência nas versões utilizadas.

### Injeção de Dependências com Koin
- **Koin**: Framework de Injeção de Dependência para Kotlin que simplifica a gestão de dependências no projeto, facilitando a injeção de repositórios, casos de uso, viewmodels e outros componentes.

### Uso de UseCase
- **Camada de UseCase**: Implementa a camada de UseCase para encapsular a lógica de negócios, promovendo uma separação clara de responsabilidades e facilitando a reutilização e teste dos casos de uso.

### Testes Unitários e Instrumentais com Mock e JUnit
- **JUnit4**: Framework para criação e execução de testes unitários, garantindo a qualidade e corretude do código.
- **MockK**: Biblioteca de mocking para Kotlin utilizada para simular comportamentos de classes de rede, repositórios e outros componentes durante os testes, permitindo a criação de testes isolados e eficazes.
- **Testes Instrumentais**: Incluem testes de UI e instrumentados utilizando Espresso.
- **Testes Unitários**: Focam na lógica de negócios e componentes isolados, assegurando que cada parte funcione conforme esperado.

### Cache de Dados com Room
- **Room**: Biblioteca oficial do Jetpack para persistência local em SQLite. Utiliza `@Entity`, `@Dao`, `@Database` e `TypeConverters` para gerenciar modelos de dados e conversões, facilitando o armazenamento e recuperação de dados locais de forma eficiente.

### Chamadas de Rede com Retrofit e Coroutines/Flow
- **Retrofit**: Biblioteca para realizar chamadas de rede de forma simplificada.
- **Coroutines**: Utilizadas para gerenciar chamadas de rede de forma assíncrona, garantindo um fluxo de dados não bloqueante e melhor performance da aplicação.
- **Flow**: Utilizado para retornar dados de forma assíncrona e reativa, permitindo a observação de mudanças nos dados e a atualização da interface de forma reativa.
- **OkHttp**: Cliente HTTP utilizado em conjunto com Retrofit para realizar requisições de rede, com suporte a logging através do `HttpLoggingInterceptor` para facilitar o debug.

---