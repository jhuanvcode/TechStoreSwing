package br.com.techstore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.List;

public class TelaProdutos extends JFrame {

    private static final String[] COLUNAS_TABELA = {
            "Código", "Nome", "Embalagem", "Tamanho", "Tipo", "Preço", "Estoque"
    };

    private final JTextField campoCodigo = new JTextField();
    private final JTextField campoNome = new JTextField();
    private final JTextField campoEmbalagem = new JTextField();
    private final JTextField campoTamanho = new JTextField();
    private final JTextField campoTipo = new JTextField();
    private final JTextField campoPreco = new JTextField();
    private final JTextField campoEstoque = new JTextField();

    private final DefaultTableModel modeloTabela = new DefaultTableModel(COLUNAS_TABELA, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tabelaProdutos = new JTable(modeloTabela);


    public TelaProdutos() {
        configurarJanela();
        add(criarPainel());
        configurarSelecaoDaTabela();
        carregarProdutos();
    }

    // MONTAGEM DA TELA

    private void configurarJanela() {
        setTitle("TechStore - Produtos");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel criarPainel() {
        JPanel painel = new JPanel();
        painel.setLayout(null);

        JLabel titulo = new JLabel("PRODUTOS");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBounds(430, 20, 200, 40);
        painel.add(titulo);

        // Coluna da esquerda
        adicionarCampoEsquerda(painel, "Código:", campoCodigo, 80);
        adicionarCampoEsquerda(painel, "Embalagem:", campoEmbalagem, 125);
        adicionarCampoEsquerda(painel, "Tipo:", campoTipo, 170);
        adicionarCampoEsquerda(painel, "Estoque:", campoEstoque, 215);

        // Coluna da direita
        adicionarCampoDireita(painel, "Nome:", campoNome, 80);
        adicionarCampoDireita(painel, "Tamanho:", campoTamanho, 125);
        adicionarCampoDireita(painel, "Preço:", campoPreco, 170);

        adicionarBotao(painel, "Salvar", 420, 215, this::salvarProduto);
        adicionarBotao(painel, "Alterar", 550, 215, this::alterarProduto);
        adicionarBotao(painel, "Limpar", 680, 215, this::limparCampos);
        adicionarBotao(painel, "Excluir", 810, 215, this::excluirProduto);

        tabelaProdutos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollTabela = new JScrollPane(tabelaProdutos);
        scrollTabela.setBounds(40, 280, 920, 300);
        painel.add(scrollTabela);

        adicionarBotao(painel, "Atualizar", 350, 600, this::atualizarLista);
        adicionarBotao(painel, "Voltar", 520, 600, this::voltarAoMenu);

        return painel;
    }

    private void adicionarCampoEsquerda(JPanel painel, String rotulo, JTextField campo, int y) {
        adicionarCampo(painel, rotulo, campo, 50, 150, 250, y);
    }

    private void adicionarCampoDireita(JPanel painel, String rotulo, JTextField campo, int y) {
        adicionarCampo(painel, rotulo, campo, 500, 600, 300, y);
    }

    private void adicionarCampo(JPanel painel, String rotulo, JTextField campo,
                                int xLabel, int xCampo, int larguraCampo, int y) {
        JLabel label = new JLabel(rotulo);
        label.setBounds(xLabel, y, 100, 25);
        campo.setBounds(xCampo, y, larguraCampo, 25);

        painel.add(label);
        painel.add(campo);
    }

    private void adicionarBotao(JPanel painel, String texto, int x, int y, Runnable acao) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, y, 120, 35);
        botao.addActionListener(e -> acao.run());
        painel.add(botao);
    }

    private void configurarSelecaoDaTabela() {
        tabelaProdutos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tabelaProdutos.getSelectedRow();

                if (linha >= 0) {
                    preencherCampos(linha);
                }
            }
        });
    }

    // AÇÕES DOS BOTÕES

    private void salvarProduto() {
        if (formularioIncompleto()) {
            mostrarMensagem("Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            BigDecimal preco = lerPreco();
            int estoque = lerEstoque();
            String codigo = texto(campoCodigo);

            if (CadastroProduto.codigoExiste(codigo)) {
                mostrarMensagem("Esse código de produto já está cadastrado!", "Código duplicado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean sucesso = CadastroProduto.salvar(
                    codigo,
                    texto(campoNome),
                    texto(campoEmbalagem),
                    texto(campoTamanho),
                    texto(campoTipo),
                    preco,
                    estoque
            );

            if (sucesso) {
                mostrarMensagem("Produto cadastrado com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                atualizarTela();
            } else {
                mostrarMensagem("Não foi possível cadastrar o produto.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            mostrarErroNumerico();
        }
    }

    private void alterarProduto() {
        if (formularioIncompleto()) {
            mostrarMensagem("Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            BigDecimal preco = lerPreco();
            int estoque = lerEstoque();

            boolean confirmado = confirmar(
                    "Deseja realmente alterar este produto?",
                    "Confirmar alteração",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (!confirmado) {
                return;
            }

            boolean sucesso = CadastroProduto.alterar(
                    texto(campoCodigo),
                    texto(campoNome),
                    texto(campoEmbalagem),
                    texto(campoTamanho),
                    texto(campoTipo),
                    preco,
                    estoque
            );

            if (sucesso) {
                mostrarMensagem("Produto alterado com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                atualizarTela();
            } else {
                mostrarMensagem("Produto não encontrado ou não foi possível alterar.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            mostrarErroNumerico();
        }
    }

    private void excluirProduto() {
        String codigo = texto(campoCodigo);

        if (codigo.isEmpty()) {
            mostrarMensagem("Selecione um produto na tabela primeiro!", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean confirmado = confirmar(
                "Tem certeza que deseja excluir este produto?\n\nCódigo: " + codigo
                        + "\nNome: " + campoNome.getText(),
                "Confirmar exclusão",
                JOptionPane.WARNING_MESSAGE
        );

        if (!confirmado) {
            return;
        }

        if (CadastroProduto.excluir(codigo)) {
            mostrarMensagem("Produto excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            atualizarTela();
        } else {
            mostrarMensagem("Não foi possível excluir o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarLista() {
        carregarProdutos();
        mostrarMensagem("Lista atualizada!", "Atualização", JOptionPane.INFORMATION_MESSAGE);
    }

    private void voltarAoMenu() {
        new TelaMenu().setVisible(true);
        dispose();
    }

    // TABELA E FORMULÁRIO

    private void carregarProdutos() {
        modeloTabela.setRowCount(0);

        List<Produto> produtos = CadastroProduto.listarProdutos();

        for (Produto produto : produtos) {
            modeloTabela.addRow(new Object[]{
                    produto.getCodigo(),
                    produto.getNome(),
                    produto.getEmbalagem(),
                    produto.getTamanho(),
                    produto.getTipo(),
                    produto.getPreco(),
                    produto.getEstoque()
            });
        }
    }

    private void preencherCampos(int linha) {
        campoCodigo.setText(celula(linha, 0));
        campoNome.setText(celula(linha, 1));
        campoEmbalagem.setText(celula(linha, 2));
        campoTamanho.setText(celula(linha, 3));
        campoTipo.setText(celula(linha, 4));
        campoPreco.setText(celula(linha, 5));
        campoEstoque.setText(celula(linha, 6));
    }

    private void limparCampos() {
        campoCodigo.setText("");
        campoNome.setText("");
        campoEmbalagem.setText("");
        campoTamanho.setText("");
        campoTipo.setText("");
        campoPreco.setText("");
        campoEstoque.setText("");

        tabelaProdutos.clearSelection();
    }

    private void atualizarTela() {
        limparCampos();
        carregarProdutos();
    }

    // UTILITÁRIOS

    private String texto(JTextField campo) {
        return campo.getText().trim();
    }

    private String celula(int linha, int coluna) {
        return modeloTabela.getValueAt(linha, coluna).toString();
    }

    private BigDecimal lerPreco() {
        return new BigDecimal(texto(campoPreco).replace(",", "."));
    }

    private int lerEstoque() {
        return Integer.parseInt(texto(campoEstoque));
    }

    private boolean formularioIncompleto() {
        return texto(campoCodigo).isEmpty()
                || texto(campoNome).isEmpty()
                || texto(campoEmbalagem).isEmpty()
                || texto(campoTamanho).isEmpty()
                || texto(campoTipo).isEmpty()
                || texto(campoPreco).isEmpty()
                || texto(campoEstoque).isEmpty();
    }

    private void mostrarErroNumerico() {
        mostrarMensagem("Preço ou estoque inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
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