package br.com.techstore;

import javax.swing.*;
import java.awt.*;

public class TelaMenu extends JFrame {

    public TelaMenu() {

        // CONFIGURAÇÃO DA JANELA

        setTitle("TechStore - Menu Principal");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // PAINEL PRINCIPAL

        JPanel painel = new JPanel();
        painel.setLayout(null);
        painel.setBackground(Estilo.AZUL_CLARO);

        // CABEÇALHO

        JPanel cabecalho = new JPanel();
        cabecalho.setLayout(null);
        cabecalho.setBackground(Estilo.AZUL_ESCURO);
        cabecalho.setBounds(0, 0, 850, 75);

        JLabel logo = new JLabel("TECHSTORE");
        logo.setFont(new Font("Arial", Font.BOLD, 24));
        logo.setForeground(Color.WHITE);
        logo.setBounds(35, 17, 200, 40);

        JLabel usuario = new JLabel("Usuário: Paulo");
        usuario.setFont(new Font("Arial", Font.PLAIN, 14));
        usuario.setForeground(Color.WHITE);
        usuario.setBounds(690, 22, 130, 30);

        cabecalho.add(logo);
        cabecalho.add(usuario);

        painel.add(cabecalho);

        // TÍTULO

        JLabel titulo = new JLabel("Menu Principal");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Estilo.AZUL_ESCURO);
        titulo.setBounds(50, 105, 400, 40);

        painel.add(titulo);

        JLabel subtitulo = new JLabel(
                "Gerencie as informações da sua loja"
        );

        subtitulo.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitulo.setForeground(Estilo.CINZA);
        subtitulo.setBounds(50, 145, 400, 30);

        painel.add(subtitulo);

        // CARD PRODUTOS

        JPanel cardProdutos = new JPanel();
        cardProdutos.setLayout(null);
        cardProdutos.setBackground(Color.WHITE);
        cardProdutos.setBounds(50, 205, 230, 150);

        JLabel tituloProdutos = new JLabel("Produtos");
        tituloProdutos.setFont(
                new Font("Arial", Font.BOLD, 20)
        );
        tituloProdutos.setForeground(Estilo.AZUL_ESCURO);
        tituloProdutos.setBounds(25, 20, 180, 30);

        JLabel textoProdutos = new JLabel(
                "Gerencie os produtos"
        );
        textoProdutos.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );
        textoProdutos.setForeground(Estilo.CINZA);
        textoProdutos.setBounds(25, 55, 180, 25);

        JButton botaoProdutos = new JButton("Acessar");
        botaoProdutos.setBounds(25, 95, 180, 35);
        Estilo.configurarBotao(
                botaoProdutos,
                Estilo.AZUL
        );

        cardProdutos.add(tituloProdutos);
        cardProdutos.add(textoProdutos);
        cardProdutos.add(botaoProdutos);

        painel.add(cardProdutos);

        // CARD CLIENTES

        JPanel cardClientes = new JPanel();
        cardClientes.setLayout(null);
        cardClientes.setBackground(Color.WHITE);
        cardClientes.setBounds(310, 205, 230, 150);

        JLabel tituloClientes = new JLabel("Clientes");
        tituloClientes.setFont(
                new Font("Arial", Font.BOLD, 20)
        );
        tituloClientes.setForeground(Estilo.AZUL_ESCURO);
        tituloClientes.setBounds(25, 20, 180, 30);

        JLabel textoClientes = new JLabel(
                "Cadastre seus clientes"
        );
        textoClientes.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );
        textoClientes.setForeground(Estilo.CINZA);
        textoClientes.setBounds(25, 55, 180, 25);

        JButton botaoClientes = new JButton("Acessar");
        botaoClientes.setBounds(25, 95, 180, 35);
        Estilo.configurarBotao(
                botaoClientes,
                Estilo.AZUL
        );

        cardClientes.add(tituloClientes);
        cardClientes.add(textoClientes);
        cardClientes.add(botaoClientes);

        painel.add(cardClientes);


        // CARD VENDEDORES


        JPanel cardVendedores = new JPanel();
        cardVendedores.setLayout(null);
        cardVendedores.setBackground(Color.WHITE);
        cardVendedores.setBounds(570, 205, 230, 150);

        JLabel tituloVendedores = new JLabel("Vendedores");
        tituloVendedores.setFont(
                new Font("Arial", Font.BOLD, 20)
        );
        tituloVendedores.setForeground(Estilo.AZUL_ESCURO);
        tituloVendedores.setBounds(25, 20, 180, 30);

        JLabel textoVendedores = new JLabel(
                "Gerencie os vendedores"
        );
        textoVendedores.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );
        textoVendedores.setForeground(Estilo.CINZA);
        textoVendedores.setBounds(25, 55, 190, 25);

        JButton botaoVendedores = new JButton("Acessar");
        botaoVendedores.setBounds(25, 95, 180, 35);
        Estilo.configurarBotao(
                botaoVendedores,
                Estilo.AZUL
        );

        cardVendedores.add(tituloVendedores);
        cardVendedores.add(textoVendedores);
        cardVendedores.add(botaoVendedores);

        painel.add(cardVendedores);

        // ÁREA INFERIOR

        JLabel informacao = new JLabel(
                "Sistema de gerenciamento TechStore"
        );

        informacao.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        informacao.setForeground(Estilo.CINZA);
        informacao.setBounds(50, 405, 350, 30);

        painel.add(informacao);

        // BOTÃO SAIR

        JButton botaoSair = new JButton("Sair do sistema");
        botaoSair.setBounds(570, 400, 230, 40);

        Estilo.configurarBotao(
                botaoSair,
                Estilo.VERMELHO
        );

        painel.add(botaoSair);

        // BOTÃO PRODUTOS

        botaoProdutos.addActionListener(e -> {

            TelaProdutos telaProdutos =
                    new TelaProdutos();

            telaProdutos.setVisible(true);

            dispose();
        });

        // BOTÃO CLIENTES

        botaoClientes.addActionListener(e -> {

            TelaClientes telaClientes =
                    new TelaClientes();

            telaClientes.setVisible(true);

            dispose();
        });

        // BOTÃO VENDEDORES

        botaoVendedores.addActionListener(e -> {

            TelaVendedores telaVendedores =
                    new TelaVendedores();

            telaVendedores.setVisible(true);

            dispose();
        });

        // BOTÃO SAIR

        botaoSair.addActionListener(e -> {

            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente sair do sistema?",
                    "Sair",
                    JOptionPane.YES_NO_OPTION
            );

            if (resposta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // FINALIZAÇÃO

        add(painel);
    }
}