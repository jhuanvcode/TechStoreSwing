package br.com.techstore;

import javax.swing.*;
import java.awt.*;

public class Estilo {

    // CORES PRINCIPAIS DO SISTEMA
    public static final Color AZUL_ESCURO = new Color(25, 35, 55);
    public static final Color AZUL = new Color(45, 100, 180);
    public static final Color AZUL_CLARO = new Color(235, 242, 250);

    public static final Color BRANCO = Color.WHITE;
    public static final Color CINZA = new Color(100, 100, 100);
    public static final Color CINZA_CLARO = new Color(240, 240, 240);

    public static final Color VERDE = new Color(40, 160, 90);
    public static final Color VERMELHO = new Color(200, 60, 60);

    // FONTE DOS TÍTULOS
    public static Font fonteTitulo() {
        return new Font("Arial", Font.BOLD, 24);
    }

    // FONTE NORMAL
    public static Font fonteNormal() {
        return new Font("Arial", Font.PLAIN, 14);
    }

    // FONTE DOS BOTÕES
    public static Font fonteBotao() {
        return new Font("Arial", Font.BOLD, 14);
    }

    // CONFIGURAÇÃO PADRÃO DOS BOTÕES
    public static void configurarBotao(
            JButton botao,
            Color cor
    ) {

        botao.setFont(fonteBotao());
        botao.setForeground(BRANCO);
        botao.setBackground(cor);

        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }

    // CONFIGURAÇÃO DOS CAMPOS
    public static void configurarCampo(
            JTextField campo
    ) {

        campo.setFont(fonteNormal());
        campo.setBackground(BRANCO);
        campo.setForeground(Color.DARK_GRAY);

        campo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(210, 210, 210)
                        ),
                        BorderFactory.createEmptyBorder(
                                5, 8, 5, 8
                        )
                )
        );
    }

    // CONFIGURAÇÃO DOS LABELS
    public static void configurarLabel(
            JLabel label
    ) {

        label.setFont(fonteNormal());
        label.setForeground(AZUL_ESCURO);
    }
}