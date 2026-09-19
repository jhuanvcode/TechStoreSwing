package br.com.techstore;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CadastroVendedor {

    private static final int FALHA = -1;

    // COMANDOS SQL

    private static final String SQL_LISTAR = """
            SELECT
                MATRICULA,
                NOME,
                [PERCENTUAL DE COMISSAO]
            FROM [TABELA DE VENDEDORES]
            ORDER BY NOME
            """;

    private static final String SQL_CONTAR_POR_MATRICULA = """
            SELECT COUNT(*)
            FROM [TABELA DE VENDEDORES]
            WHERE MATRICULA = ?
            """;

    private static final String SQL_INSERIR = """
            INSERT INTO [TABELA DE VENDEDORES]
            (
                MATRICULA,
                NOME,
                [PERCENTUAL DE COMISSAO]
            )
            VALUES (?, ?, ?)
            """;

    private static final String SQL_ALTERAR = """
            UPDATE [TABELA DE VENDEDORES]
            SET
                NOME = ?,
                [PERCENTUAL DE COMISSAO] = ?
            WHERE MATRICULA = ?
            """;

    private static final String SQL_EXCLUIR = """
            DELETE FROM [TABELA DE VENDEDORES]
            WHERE MATRICULA = ?
            """;

    // LISTAR VENDEDORES

    public static List<Vendedor> listarVendedores() {

        List<Vendedor> vendedores = new ArrayList<>();

        try (Connection conexao = ConexaoBanco.conectar()) {

            if (conexao == null) {
                return vendedores;
            }

            try (PreparedStatement comando = conexao.prepareStatement(SQL_LISTAR);
                 ResultSet resultado = comando.executeQuery()) {

                while (resultado.next()) {
                    vendedores.add(criarVendedor(resultado));
                }
            }

        } catch (SQLException e) {
            registrarErro("Erro ao listar vendedores:", e);
        }

        return vendedores;
    }

    // VERIFICAR SE A MATRÍCULA EXISTE

    public static boolean matriculaExiste(String matricula) {

        try (Connection conexao = ConexaoBanco.conectar()) {

            if (conexao == null) {
                return false;
            }

            try (PreparedStatement comando = conexao.prepareStatement(SQL_CONTAR_POR_MATRICULA)) {

                comando.setString(1, matricula);

                try (ResultSet resultado = comando.executeQuery()) {
                    resultado.next();
                    return resultado.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            registrarErro("Erro ao verificar matrícula:", e);
            return false;
        }
    }

    // SALVAR NOVO VENDEDOR

    public static boolean salvar(
            String matricula,
            String nome,
            BigDecimal percentualComissao) {

        int linhas = executarAtualizacao(SQL_INSERIR, comando -> {
            comando.setString(1, matricula);
            comando.setString(2, nome);
            comando.setBigDecimal(3, percentualComissao);
        }, "Erro ao cadastrar vendedor:");

        return linhas != FALHA;
    }

    // ALTERAR VENDEDOR

    public static boolean alterar(
            String matricula,
            String nome,
            BigDecimal percentualComissao) {

        int linhas = executarAtualizacao(SQL_ALTERAR, comando -> {
            comando.setString(1, nome);
            comando.setBigDecimal(2, percentualComissao);
            comando.setString(3, matricula);
        }, "Erro ao alterar vendedor:");

        return linhas > 0;
    }

    // EXCLUIR VENDEDOR

    public static boolean excluir(String matricula) {

        int linhas = executarAtualizacao(SQL_EXCLUIR,
                comando -> comando.setString(1, matricula),
                "Erro ao excluir vendedor:");

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

    private static Vendedor criarVendedor(ResultSet resultado) throws SQLException {
        return new Vendedor(
                resultado.getString("MATRICULA"),
                resultado.getString("NOME"),
                resultado.getBigDecimal("PERCENTUAL DE COMISSAO")
        );
    }

    private static void registrarErro(String mensagem, SQLException e) {
        System.out.println(mensagem);
        e.printStackTrace();
    }
}