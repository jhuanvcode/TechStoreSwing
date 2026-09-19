package br.com.techstore;

import java.sql.Connection;

public class TesteConexao {

    public static void main(String[] args) {

        Connection conexao = ConexaoBanco.conectar();

        if (conexao != null) {

            System.out.println("BANCO TECH_VENDAS CONECTADO!");

            try {
                conexao.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            System.out.println("NÃO FOI POSSÍVEL CONECTAR!");
        }
    }
}