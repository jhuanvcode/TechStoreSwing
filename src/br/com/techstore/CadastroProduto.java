package br.com.techstore;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CadastroProduto {

    private static final int FALHA = -1;

    // COMANDOS SQL

    private static final String SQL_LISTAR = """
            SELECT
                [CODIGO DO PRODUTO],
                [NOME DO PRODUTO],
                EMBALAGEM,
                TAMANHO,
                TIPO,
                [PRECO DE LISTA],
                ESTOQUE
            FROM [TABELA DE PRODUTOS]
            ORDER BY [NOME DO PRODUTO]
            """;

    private static final String SQL_CONTAR_POR_CODIGO = """
            SELECT COUNT(*)
            FROM [TABELA DE PRODUTOS]
            WHERE [CODIGO DO PRODUTO] = ?
            """;

    private static final String SQL_INSERIR = """
            INSERT INTO [TABELA DE PRODUTOS]
            (
                [CODIGO DO PRODUTO],
                [NOME DO PRODUTO],
                EMBALAGEM,
                TAMANHO,
                TIPO,
                [PRECO DE LISTA],
                ESTOQUE
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SQL_ALTERAR = """
            UPDATE [TABELA DE PRODUTOS]
            SET
                [NOME DO PRODUTO] = ?,
                EMBALAGEM = ?,
                TAMANHO = ?,
                TIPO = ?,
                [PRECO DE LISTA] = ?,
                ESTOQUE = ?
            WHERE [CODIGO DO PRODUTO] = ?
            """;

    private static final String SQL_EXCLUIR = """
            DELETE FROM [TABELA DE PRODUTOS]
            WHERE [CODIGO DO PRODUTO] = ?
            """;

    // LISTAR PRODUTOS

    public static List<Produto> listarProdutos() {

        List<Produto> produtos = new ArrayList<>();

        try (Connection conexao = ConexaoBanco.conectar()) {

            if (conexao == null) {
                return produtos;
            }

            try (PreparedStatement comando = conexao.prepareStatement(SQL_LISTAR);
                 ResultSet resultado = comando.executeQuery()) {

                while (resultado.next()) {
                    produtos.add(criarProduto(resultado));
                }
            }

        } catch (SQLException e) {
            registrarErro("Erro ao listar produtos:", e);
        }

        return produtos;
    }

    // VERIFICA SE O CÓDIGO JÁ EXISTE

    public static boolean codigoExiste(String codigo) {

        try (Connection conexao = ConexaoBanco.conectar()) {

            if (conexao == null) {
                return false;
            }

            try (PreparedStatement comando = conexao.prepareStatement(SQL_CONTAR_POR_CODIGO)) {

                comando.setString(1, codigo);

                try (ResultSet resultado = comando.executeQuery()) {
                    resultado.next();
                    return resultado.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            registrarErro("Erro ao verificar código:", e);
            return false;
        }
    }

    // SALVAR NOVO PRODUTO

    public static boolean salvar(
            String codigo,
            String nome,
            String embalagem,
            String tamanho,
            String tipo,
            BigDecimal preco,
            int estoque) {

        int linhas = executarAtualizacao(SQL_INSERIR, comando -> {
            comando.setString(1, codigo);
            comando.setString(2, nome);
            comando.setString(3, embalagem);
            comando.setString(4, tamanho);
            comando.setString(5, tipo);
            comando.setBigDecimal(6, preco);
            comando.setInt(7, estoque);
        }, "Erro ao cadastrar produto:");

        return linhas != FALHA;
    }

    // ALTERAR PRODUTO

    public static boolean alterar(
            String codigo,
            String nome,
            String embalagem,
            String tamanho,
            String tipo,
            BigDecimal preco,
            int estoque) {

        int linhas = executarAtualizacao(SQL_ALTERAR, comando -> {
            comando.setString(1, nome);
            comando.setString(2, embalagem);
            comando.setString(3, tamanho);
            comando.setString(4, tipo);
            comando.setBigDecimal(5, preco);
            comando.setInt(6, estoque);
            comando.setString(7, codigo);
        }, "Erro ao alterar produto:");

        return linhas > 0;
    }

    // EXCLUIR PRODUTO

    public static boolean excluir(String codigo) {

        int linhas = executarAtualizacao(SQL_EXCLUIR,
                comando -> comando.setString(1, codigo),
                "Erro ao excluir produto:");

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

    private static Produto criarProduto(ResultSet resultado) throws SQLException {
        return new Produto(
                resultado.getString("CODIGO DO PRODUTO"),
                resultado.getString("NOME DO PRODUTO"),
                resultado.getString("EMBALAGEM"),
                resultado.getString("TAMANHO"),
                resultado.getString("TIPO"),
                resultado.getBigDecimal("PRECO DE LISTA"),
                resultado.getInt("ESTOQUE")
        );
    }

    private static void registrarErro(String mensagem, SQLException e) {
        System.out.println(mensagem);
        e.printStackTrace();
    }
}