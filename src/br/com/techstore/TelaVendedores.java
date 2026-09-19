package br.com.techstore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.List;

public class TelaVendedores extends JFrame {

    private static final String[] COLUNAS_TABELA = {
            "Matrícula", "Nome", "Comissão (%)"
    };

    private final JTextField campoMatricula = new JTextField();
    private final JTextField campoNome = new JTextField();
    private final JTextField campoComissao = new JTextField();

    private final DefaultTableModel modeloTabela = new DefaultTableModel(COLUNAS_TABELA, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tabelaVendedores = new JTable(modeloTabela);


    public TelaVendedores() {
        configurarJanela();
        add(criarPainel());
        configurarSelecaoDaTabela();
        carregarVendedores();
    }

    // MONTAGEM DA TELA

    private void configurarJanela() {
        setTitle("TechStore - Vendedores");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel criarPainel() {
        JPanel painel = new JPanel();
        painel.setLayout(null);

        JLabel titulo = new JLabel("VENDEDORES");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBounds(320, 20, 200, 40);
        painel.add(titulo);

        adicionarCampo(painel, "Matrícula:", campoMatricula, 60, 160, 200, 80);
        adicionarCampo(painel, "Nome:", campoNome, 400, 500, 220, 80);
        adicionarCampo(painel, "Comissão (%):", campoComissao, 60, 160, 200, 125);

        adicionarBotao(painel, "Salvar", 400, 120, 100, this::salvarVendedor);
        adicionarBotao(painel, "Alterar", 510, 120, 100, this::alterarVendedor);
        adicionarBotao(painel, "Limpar", 620, 120, 100, this::limparCampos);
        adicionarBotao(painel, "Excluir", 400, 165, 100, this::excluirVendedor);

        tabelaVendedores.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollTabela = new JScrollPane(tabelaVendedores);
        scrollTabela.setBounds(50, 220, 700, 280);
        painel.add(scrollTabela);

        adicionarBotao(painel, "Atualizar", 250, 530, 120, this::atualizarLista);
        adicionarBotao(painel, "Voltar", 410, 530, 120, this::voltarAoMenu);

        return painel;
    }

    private void adicionarCampo(JPanel painel, String rotulo, JTextField campo,
                                int xLabel, int xCampo, int larguraCampo, int y) {
        JLabel label = new JLabel(rotulo);
        label.setBounds(xLabel, y, 100, 25);
        campo.setBounds(xCampo, y, larguraCampo, 25);

        painel.add(label);
        painel.add(campo);
    }

    private void adicionarBotao(JPanel painel, String texto, int x, int y, int largura, Runnable acao) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, y, largura, 35);
        botao.addActionListener(e -> acao.run());
        painel.add(botao);
    }

    private void configurarSelecaoDaTabela() {
        tabelaVendedores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tabelaVendedores.getSelectedRow();

                if (linha >= 0) {
                    preencherCampos(linha);
                }
            }
        });
    }

    // AÇÕES DOS BOTÕES

    private void salvarVendedor() {
        if (formularioIncompleto()) {
            mostrarMensagem("Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            BigDecimal comissao = lerComissao();
            String matricula = texto(campoMatricula);

            if (CadastroVendedor.matriculaExiste(matricula)) {
                mostrarMensagem("Essa matrícula já está cadastrada!", "Matrícula duplicada",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean sucesso = CadastroVendedor.salvar(matricula, texto(campoNome), comissao);

            if (sucesso) {
                mostrarMensagem("Vendedor cadastrado com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                atualizarTela();
            } else {
                mostrarMensagem("Não foi possível cadastrar o vendedor.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            mostrarErroComissao();
        }
    }

    private void alterarVendedor() {
        if (formularioIncompleto()) {
            mostrarMensagem("Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            BigDecimal comissao = lerComissao();

            boolean confirmado = confirmar(
                    "Deseja realmente alterar este vendedor?",
                    "Confirmar alteração",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (!confirmado) {
                return;
            }

            boolean sucesso = CadastroVendedor.alterar(
                    texto(campoMatricula),
                    texto(campoNome),
                    comissao
            );

            if (sucesso) {
                mostrarMensagem("Vendedor alterado com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                atualizarTela();
            } else {
                mostrarMensagem("Vendedor não encontrado ou não foi possível alterar.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            mostrarErroComissao();
        }
    }

    private void excluirVendedor() {
        String matricula = texto(campoMatricula);

        if (matricula.isEmpty()) {
            mostrarMensagem("Selecione um vendedor na tabela primeiro!", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean confirmado = confirmar(
                "Tem certeza que deseja excluir este vendedor?\n\nMatrícula: " + matricula
                        + "\nNome: " + texto(campoNome),
                "Confirmar exclusão",
                JOptionPane.WARNING_MESSAGE
        );

        if (!confirmado) {
            return;
        }

        if (CadastroVendedor.excluir(matricula)) {
            mostrarMensagem("Vendedor excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            atualizarTela();
        } else {
            mostrarMensagem("Não foi possível excluir o vendedor.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarLista() {
        carregarVendedores();
        mostrarMensagem("Lista de vendedores atualizada!", "Atualização",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void voltarAoMenu() {
        new TelaMenu().setVisible(true);
        dispose();
    }

    // TABELA E FORMULÁRIO

    private void carregarVendedores() {
        modeloTabela.setRowCount(0);

        List<Vendedor> vendedores = CadastroVendedor.listarVendedores();

        for (Vendedor vendedor : vendedores) {
            modeloTabela.addRow(new Object[]{
                    vendedor.getMatricula(),
                    vendedor.getNome(),
                    vendedor.getPercentualComissao()
            });
        }
    }

    private void preencherCampos(int linha) {
        campoMatricula.setText(celula(linha, 0));
        campoNome.setText(celula(linha, 1));
        campoComissao.setText(celula(linha, 2));
    }

    private void limparCampos() {
        campoMatricula.setText("");
        campoNome.setText("");
        campoComissao.setText("");

        tabelaVendedores.clearSelection();
    }

    private void atualizarTela() {
        limparCampos();
        carregarVendedores();
    }

    // UTILITÁRIOS

    private String texto(JTextField campo) {
        return campo.getText().trim();
    }

    private String celula(int linha, int coluna) {
        return modeloTabela.getValueAt(linha, coluna).toString();
    }

    private BigDecimal lerComissao() {
        return new BigDecimal(texto(campoComissao).replace(",", "."));
    }

    private boolean formularioIncompleto() {
        return texto(campoMatricula).isEmpty()
                || texto(campoNome).isEmpty()
                || texto(campoComissao).isEmpty();
    }

    private void mostrarErroComissao() {
        mostrarMensagem("O percentual de comissão é inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarMensagem(String mensagem, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }

    private boolean confirmar(String mensagem, String titulo, int tipo) {
        int resposta = JOptionPane.showConfirmDialog(
                this, mensagem, titulo, JOptionPane.YES_NO_OPTION, tipo
        );
        return resposta == JOptionPane.YES_OPTION;
    }
}