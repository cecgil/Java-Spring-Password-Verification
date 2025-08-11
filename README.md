# 🔐 CRUD com Spring Boot + MySQL - Validação de Força de Senha

Projeto CRUD desenvolvido com **Spring Boot**, utilizando **MySQL** e **DBeaver** como ferramentas de banco de dados.  
O diferencial deste sistema é que, ao cadastrar uma senha, o backend realiza a **criptografia** e avalia se ela é **fraca**, **média** ou **forte**, com base nos critérios definidos.

## 📌 Funcionalidades

- **CRUD completo** (Create, Read, Update, Delete) de usuários.
- Criptografia da senha antes de salvar no banco.
- Avaliação da força da senha:
  - **Fraca** → Apenas letras ou números, tamanho pequeno.
  - **Média** → Combinação de letras e números, tamanho moderado.
  - **Forte** → Letras maiúsculas, minúsculas, números e caracteres especiais, tamanho adequado.
- Integração com banco **MySQL** (gerenciado via **DBeaver**).
- API REST com endpoints organizados.

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3+**
- **Spring Data JPA**
- **MySQL**
- **Lombok**
- **BCrypt** para criptografia de senha
- **Maven**
- **DBeaver** (gerenciamento e visualização do banco)
