# Projeto Biblioteca - Spring Boot (exemplo)

Projeto de exemplo para gerenciar livros, usuários e empréstimos usando:
- Spring Boot (Web, JPA, Thymeleaf)
- H2 em memória (fácil para desenvolvimento)
- Java 17

## Conteúdo
Este ZIP contém o código-fonte pronto para abrir no IntelliJ IDEA.

## Como abrir no IntelliJ
1. Descompacte o arquivo `biblioteca.zip`.
2. Abra o IntelliJ IDEA -> `File` -> `Open...` e selecione a pasta `biblioteca` (a que contém o `pom.xml`).
3. Caso o IntelliJ peça para importar como projeto Maven, aceite.
4. Configure o SDK do projeto para Java 17 (File -> Project Structure -> Project SDK).
5. Rode a classe `com.exemplo.biblioteca.BibliotecaApplication` como aplicação Spring Boot.

## URLs úteis (após iniciar)
- Lista de livros: http://localhost:8080/livros
- Lista de usuários: http://localhost:8080/usuarios
- Empréstimos: http://localhost:8080/emprestimos
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: jdbc:h2:mem:biblioteca
  - User: sa
  - Password: (vazio)
