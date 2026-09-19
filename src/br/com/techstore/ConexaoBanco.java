package br.com.techstore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

    public static Connection conectar() {

        String url =
                "jdbc:sqlserver://127.0.0.1:1434;" +
                        "databaseName=TECH_VENDAS;" +
                        "encrypt=true;" +
                        "trustServerCertificate=true";

        String usuario = "user";
        String senha = "senha";

        try {

            Connection conexao = DriverManager.getConnection(
                    url,
                    usuario,
                    senha
            );

            System.out.println("Conectado com sucesso!");

            return conexao;

        } catch (SQLException e) {

            System.out.println("Erro ao conectar com o banco!");
            e.printStackTrace();

            return null;
        }
    }
}