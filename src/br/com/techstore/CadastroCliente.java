package br.com.techstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CadastroCliente {

    private static final int FALHA = -1;

    // COMANDOS SQL

    private static final String SQL_CONTAR_POR_CPF = """
            SELECT COUNT(*)
            FROM [TABELA DE CLIENTES]
            WHERE CPF = ?
            """;

    private static final String SQL_INSERIR = """
            INSERT INTO [TABELA DE CLIENTES]
            (CPF, NOME, RUA, TELEFONE, [E-MAIL])
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String SQL_LISTAR = """
            SELECT CPF, NOME, TELEFONE, [E-MAIL], RUA
            FROM [TABELA DE CLIENTES]
            ORDER BY NOME
            """;

    private static final String SQL_ALTERAR = """
            UPDATE [TABELA DE CLIENTES]
            SET
                NOME = ?,
                RUA = ?,
                TELEFONE = ?,
                [E-MAIL] = ?
            WHERE CPF = ?
            """;

    private static final String SQL_EXCLUIR = """
            DELETE FROM [TABELA DE CLIENTES]
            WHERE CPF = ?
            """;

    // VERIFICA SE O CPF JÁ ESTÁ CADASTRADO

    public static boolean cpfExiste(String cpf) {

        try (Connection conexao = ConexaoBanco.conectar()) {

            if (conexao == null) {
                return false;
            }

            try (PreparedStatement comando = conexao.prepareStatement(SQL_CONTAR_POR_CPF)) {

                comando.setString(1, cpf);

                try (ResultSet resultado = comando.executeQuery()) {
                    resultado.next();
                    return resultado.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            registrarErro("Erro ao verificar CPF:", e);
            return false;
        }
    }

    // SALVAR NOVO CLIENTE

    public static boolean salvar(
            String nome,
            String cpf,
            String telefone,
            String email,
            String endereco) {

        int linhas = executarAtualizacao(SQL_INSERIR, comando -> {
            comando.setString(1, cpf);
            comando.setString(2, nome);
            comando.setString(3, endereco);
            comando.setString(4, telefone);
            comando.setString(5, email);
        }, "Erro ao cadastrar cliente:");

        return linhas != FALHA;
    }

    // LISTAR CLIENTES

    public static List<Cliente> listarClientes() {

        List<Cliente> clientes = new ArrayList<>();

        try (Connection conexao = ConexaoBanco.conectar()) {

            if (conexao == null) {
                return clientes;
            }

            try (PreparedStatement comando = conexao.prepareStatement(SQL_LISTAR);
                 ResultSet resultado = comando.executeQuery()) {

                while (resultado.next()) {
                    clientes.add(criarCliente(resultado));
                }
            }

        } catch (SQLException e) {
            registrarErro("Erro ao listar clientes:", e);
        }

        return clientes;
    }

    // ALTERAR CLIENTE

    public static boolean alterar(
            String cpf,
            String nome,
            String telefone,
            String email,
            String endereco) {

        int linhas = executarAtualizacao(SQL_ALTERAR, comando -> {
            comando.setString(1, nome);
            comando.setString(2, endereco);
            comando.setString(3, telefone);
            comando.setString(4, email);
            comando.setString(5, cpf);
        }, "Erro ao alterar cliente:");

        return linhas > 0;
    }

    // EXCLUIR CLIENTE

    public static boolean excluir(String cpf) {

        int linhas = executarAtualizacao(SQL_EXCLUIR,
                comando -> comando.setString(1, cpf),
                "Erro ao excluir cliente:");

        return linhas > 0;
    }

    // MÉTODOS AUXILIARES

    @FunctionalInterface
    private interface PreencherParametros {
        void preencher(PreparedStatement comando) throws SQLException;
    }

    private static int executarAtualizacao(
            String sql,
            PreencherParametros parametros,
            String mensagemErro) {

        try (Connection conexao = ConexaoBanco.conectar()) {

            if (conexao == null) {
                return FALHA;
            }

            try (PreparedStatement comando = conexao.prepareStatement(sql)) {
                parametros.preencher(comando);
                return comando.executeUpdate();
            }

        } catch (SQLException e) {
            registrarErro(mensagemErro, e);
            return FALHA;
        }
    }

    private static Cliente criarCliente(ResultSet resultado) throws SQLException {
        return new Cliente(
                resultado.getString("CPF"),
                resultado.getString("NOME"),
                resultado.getString("TELEFONE"),
                resultado.getString("E-MAIL"),
                resultado.getString("RUA")
        );
    }

    private static void registrarErro(String mensagem, SQLException e) {
        System.out.println(mensagem);
        e.printStackTrace();
    }
}