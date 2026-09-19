package br.com.techstore;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;

    public TelaLogin() {

        // CONFIGURAÇÃO DA JANELA

        setTitle("TechStore - Login");
        setSize(500, 500);
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
        cabecalho.setBounds(0, 0, 500, 100);

        JLabel logo = new JLabel("TECHSTORE");

        logo.setFont(
                new Font("Arial", Font.BOLD, 30)
        );

        logo.setForeground(Color.WHITE);
        logo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        logo.setBounds(100, 25, 300, 45);

        cabecalho.add(logo);

        painel.add(cabecalho);

        // TÍTULO

        JLabel titulo = new JLabel("Acesso ao sistema");

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titulo.setForeground(
                Estilo.AZUL_ESCURO
        );

        titulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titulo.setBounds(80, 130, 340, 35);

        painel.add(titulo);

        // SUBTÍTULO

        JLabel subtitulo = new JLabel(
                "Entre com suas credenciais para continuar"
        );

        subtitulo.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        subtitulo.setForeground(
                Estilo.CINZA
        );

        subtitulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        subtitulo.setBounds(50, 165, 400, 25);

        painel.add(subtitulo);

        // USUÁRIO

        JLabel labelUsuario = new JLabel("Usuário");

        labelUsuario.setBounds(
                80, 205, 340, 25
        );

        Estilo.configurarLabel(labelUsuario);

        painel.add(labelUsuario);

        campoUsuario = new JTextField();

        campoUsuario.setBounds(
                80, 230, 340, 38
        );

        Estilo.configurarCampo(campoUsuario);

        painel.add(campoUsuario);

        // SENHA

        JLabel labelSenha = new JLabel("Senha");

        labelSenha.setBounds(
                80, 280, 340, 25
        );

        Estilo.configurarLabel(labelSenha);

        painel.add(labelSenha);

        campoSenha = new JPasswordField();

        campoSenha.setBounds(
                80, 305, 340, 38
        );

        Estilo.configurarCampo(campoSenha);

        painel.add(campoSenha);

        // BOTÃO ENTRAR

        JButton botaoEntrar =
                new JButton("Entrar");

        botaoEntrar.setBounds(
                80, 370, 160, 40
        );

        Estilo.configurarBotao(
                botaoEntrar,
                Estilo.VERDE
        );

        painel.add(botaoEntrar);

        // BOTÃO LIMPAR

        JButton botaoLimpar =
                new JButton("Limpar");

        botaoLimpar.setBounds(
                260, 370, 160, 40
        );

        Estilo.configurarBotao(
                botaoLimpar,
                Estilo.CINZA
        );

        painel.add(botaoLimpar);

        // RODAPÉ

        JLabel rodape =
                new JLabel("TechStore • Sistema de gerenciamento");

        rodape.setFont(
                new Font("Arial", Font.PLAIN, 11)
        );

        rodape.setForeground(
                Estilo.CINZA
        );

        rodape.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        rodape.setBounds(
                50, 425, 400, 20
        );

        painel.add(rodape);

        // BOTÃO ENTRAR

        botaoEntrar.addActionListener(e -> {

            String usuario =
                    campoUsuario.getText();

            String senha =
                    new String(campoSenha.getPassword());

            // USUÁRIO E SENHA AUTORIZADOS

            if (usuario.equals("Paulo") &&
                    senha.equals("justo")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login realizado com sucesso!",
                        "Acesso autorizado",
                        JOptionPane.INFORMATION_MESSAGE
                );

                TelaMenu telaMenu =
                        new TelaMenu();

                telaMenu.setVisible(true);

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Usuário ou senha incorretos!",
                        "Acesso negado",
                        JOptionPane.ERROR_MESSAGE
                );

                campoSenha.setText("");
                campoSenha.requestFocus();
            }
        });

        // BOTÃO LIMPAR

        botaoLimpar.addActionListener(e -> {

            campoUsuario.setText("");
            campoSenha.setText("");

            campoUsuario.requestFocus();
        });

        // ENTER PARA FAZER LOGIN

        campoSenha.addActionListener(e ->
                botaoEntrar.doClick()
        );

        // FINALIZAÇÃO

        add(painel);

        campoUsuario.requestFocus();
    }
}