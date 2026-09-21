# 🛒 TechStore Swing

**TechStore Swing** é um sistema desktop de gerenciamento desenvolvido em **Java Swing**, criado como projeto acadêmico para simular o gerenciamento de uma loja de tecnologia. O sistema permite cadastrar, consultar, alterar e excluir **clientes, produtos e vendedores**, utilizando **SQL Server** como banco de dados e **JDBC** para realizar a comunicação entre a aplicação Java e o banco.

## 📋 Visão Geral

**TechStore Swing** combina uma interface gráfica desenvolvida com Java Swing com um banco de dados relacional para gerenciamento das informações da loja.

O sistema possui:

- **Gerenciamento de Clientes**: cadastro, consulta, alteração e exclusão de clientes
- **Gerenciamento de Produtos**: cadastro, consulta, alteração e exclusão de produtos
- **Gerenciamento de Vendedores**: cadastro, consulta, alteração e exclusão de vendedores
- **Validação de dados**: verificação de campos obrigatórios e prevenção de registros duplicados
- **Integração com SQL Server**: armazenamento e consulta dos dados diretamente no banco
- **Interface gráfica**: telas desenvolvidas utilizando Java Swing

## 🖥️ Como Funciona

### 🔐 Acesso ao Sistema

O sistema possui uma tela inicial de acesso que direciona o usuário para o menu principal.

A partir do menu, é possível acessar:

- 📦 Produtos
- 👥 Clientes
- 👨‍💼 Vendedores
- 🚪 Sair

### 👥 Clientes

A tela de clientes permite:

- Cadastrar novos clientes
- Consultar clientes cadastrados
- Alterar informações
- Excluir clientes
- Verificar se o CPF já está cadastrado

Informações armazenadas:

| Campo | Descrição |
|------|-----------|
| **CPF** | Identificação do cliente |
| **Nome** | Nome completo |
| **Telefone** | Telefone para contato |
| **E-mail** | E-mail do cliente |
| **Endereço** | Endereço do cliente |

### 📦 Produtos

A tela de produtos permite:

- Cadastrar novos produtos
- Consultar produtos cadastrados
- Alterar informações
- Excluir produtos
- Verificar se o código do produto já existe
- Consultar estoque

Informações armazenadas:

| Campo | Descrição |
|------|-----------|
| **Código do Produto** | Identificação do produto |
| **Nome do Produto** | Nome do produto |
| **Embalagem** | Tipo de embalagem |
| **Tamanho** | Tamanho do produto |
| **Tipo** | Categoria/tipo do produto |
| **Preço de Lista** | Preço do produto |
| **Estoque** | Quantidade disponível |

### 👨‍💼 Vendedores

A tela de vendedores permite:

- Cadastrar novos vendedores
- Consultar vendedores cadastrados
- Alterar informações
- Excluir vendedores
- Verificar se a matrícula já existe

Informações armazenadas:

| Campo | Descrição |
|------|-----------|
| **Matrícula** | Identificação do vendedor |
| **Nome** | Nome do vendedor |
| **Percentual de Comissão** | Comissão recebida pelo vendedor |

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java
- **Interface gráfica:** Java Swing
- **Banco de dados:** Microsoft SQL Server
- **Comunicação com banco:** JDBC
- **Driver:** Microsoft SQL Server JDBC Driver
- **Build e dependências:** Maven
- **IDE utilizada:** IntelliJ IDEA

## 📦 Instalação e Execução

### 1. Clone o repositório

```bash
git clone https://github.com/jhuanvcode/TechStoreSwing.git
cd TechStoreSwing
```
### 2. Requisitos

Para executar o projeto, é necessário ter instalado:

- Java JDK 25 ou compatível com a configuração do projeto
- Maven
- Microsoft SQL Server
- IntelliJ IDEA ou outra IDE compatível com projetos Maven

## 3. Banco de Dados

O sistema utiliza o banco de dados **TECH_VENDAS**, desenvolvido em **Microsoft SQL Server**.

### 📊 Tabelas utilizadas

O sistema trabalha com as seguintes tabelas:

- 👥 **TABELA DE CLIENTES** — Armazena os dados dos clientes cadastrados.
- 📦 **TABELA DE PRODUTOS** — Armazena os produtos, preços e informações de estoque.
- 👨‍💼 **TABELA DE VENDEDORES** — Armazena os dados dos vendedores e seus percentuais de comissão.

## 4. Configuração da conexão

Configure a conexão localmente no arquivo responsável pela conexão com o SQL Server.

Exemplo:
```
String url =
        "jdbc:sqlserver://127.0.0.1:1434;" +
        "databaseName=TECH_VENDAS;" +
        "encrypt=true;" +
        "trustServerCertificate=true";
```
## 🎨 Estrutura do Projeto

```
TechStoreSwing/
├── src/
│   └── br/
│       └── com/
│           └── techstore/
│               ├── Cliente.java
│               ├── Produto.java
│               ├── Vendedor.java
│               │
│               ├── CadastroCliente.java
│               ├── CadastroProduto.java
│               ├── CadastroVendedor.java
│               │
│               ├── ConexaoBanco.java
│               ├── TesteConexao.java
│               │
│               ├── TelaClientes.java
│               ├── TelaProdutos.java
│               ├── TelaVendedores.java
│               └── TelaMenu.java
│
├── pom.xml
├── README.md
└── .gitignore
```

## 🗄️ Banco de Dados

A aplicação utiliza JDBC para estabelecer a comunicação entre o Java e o SQL Server.

O fluxo básico da aplicação é:

```
Interface Java Swing
        ↓
Classes de cadastro
        ↓
JDBC
        ↓
SQL Server
        ↓
TECH_VENDAS
```
## As operações realizadas pelo sistema incluem:

| Comando | Função |
|---------|--------|
| `SELECT` | Consultar registros |
| `INSERT` | Cadastrar registros |
| `UPDATE` | Alterar registros |
| `DELETE` | Excluir registros |
✨ Funcionalidades Implementadas

### 👥 Cadastro de Clientes

-Cadastro de clientes
-Consulta de clientes
-Alteração de clientes
-Exclusão de clientes
-Validação de CPF duplicado

### 📦 Cadastro de Produtos

-Cadastro de produtos
-Consulta de produtos
-Alteração de produtos
-Exclusão de produtos
-Controle de estoque
-Validação de código duplicado

### 👨‍💼 Cadastro de Vendedores

-Cadastro de vendedores
-Consulta de vendedores
-Alteração de vendedores
-Exclusão de vendedores
-Controle de percentual de comissão
-Validação de matrícula duplicada

### 🗄️ Banco de Dados

-Conexão com SQL Server
-Operações CRUD
-PreparedStatement
-Consultas parametrizadas
-Integração através de JDBC

### 🖥️ Interface

-Java Swing
-Múltiplas telas
-Tabelas para visualização dos registros
-Botões de cadastro, alteração, exclusão e atualização
-Navegação entre as telas

## 🧪 Exemplo de Uso

1. Execute a aplicação.
2. Acesse o menu principal.
3. Escolha **Clientes**, **Produtos** ou **Vendedores**.
4. Cadastre um novo registro.
5. Selecione um registro na tabela.
6. Utilize **Alterar** para modificar seus dados.
7. Utilize **Excluir** para remover um registro.
8. Utilize **Atualizar** para recarregar os dados do SQL Server.

## 📚 Objetivo Acadêmico

O projeto foi desenvolvido com objetivo educacional, colocando em prática conceitos de:

- Programação Orientada a Objetos
- Java Swing
- JDBC
- SQL
- Banco de dados relacional
- Operações CRUD
- Maven
- Integração entre aplicação e banco de dados
- Desenvolvimento de interfaces gráficas
  
## 📄 Licença

Projeto desenvolvido para fins educacionais e acadêmicos.

⭐ Projeto desenvolvido utilizando Java + Swing + JDBC + SQL Server + Maven.
