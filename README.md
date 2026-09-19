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
git clone https://github.com/SEU-USUARIO/TechStoreSwing.git
cd TechStoreSwing# TechStoreSwing
