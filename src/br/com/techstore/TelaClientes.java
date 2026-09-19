package br.com.techstore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.util.List;

public class TelaClientes extends JFrame {

    private static final String[] COLUNAS_TABELA = {
            "CPF", "Nome", "Telefone", "E-mail", "Endereço"
    };

    private final JTextField campoNome = new JTextField();
    private final JTextField campoCpf = new JTextField();
    private final JTextField campoTelefone = new JTextField();
    private final JTextField campoEmail = new JTextField();
    private final JTextField campoEndereco = new JTextField();

    private final DefaultTableModel modeloTabela = new DefaultTableModel(COLUNAS_TABELA, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tabelaClientes = new JTable(modeloTabela);


    public TelaClientes() {
        configurarJanela();
        add(criarPainel());
        configurarSelecaoDaTabela();
        carregarClientes();
    }


    // =====================================================
    // MONTAGEM DA TELA
    // =====================================================

    private void configurarJanela() {
        setTitle("TechStore - Cadastro de Clientes");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel criarPainel() {
        JPanel painel = new JPanel();
        painel.setLayout(null);

        adicionarTitulo(painel, "CADASTRO DE CLIENTES", 22, 250, 25, 350, 40);

        adicionarCampo(painel, "Nome:", campoNome, 90);
        adicionarCampo(painel, "CPF:", campoCpf, 135);
        adicionarCampo(painel, "Telefone:", campoTelefone, 180);
        adicionarCampo(painel, "E-mail:", campoEmail, 225);
        adicionarCampo(painel, "Endereço:", campoEndereco, 270);

        adicionarBotao(painel, "Salvar", 90, this::salvarCliente);
        adicionarBotao(painel, "Alterar", 230, this::alterarCliente);
        adicionarBotao(painel, "Excluir", 370, this::excluirCliente);
        adicionarBotao(painel, "Limpar", 510, this::limparCampos);
        adicionarBotao(painel, "Voltar", 650, this::voltarAoMenu);

        adicionarTitulo(painel, "CLIENTES CADASTRADOS", 18, 60, 390, 300, 30);

        tabelaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollTabela = new JScrollPane(tabelaClientes);
        scrollTabela.setBounds(60, 425, 720, 150);
        painel.add(scrollTabela);

        return painel;
    }

    private void adicionarTitulo(JPanel painel, String texto, int tamanhoFonte,
                                 int x, int y, int largura, int altura) {
        JLabel titulo = new JLabel(texto);
        titulo.setFont(new Font("Arial", Font.BOLD, tamanhoFonte));
        titulo.setBounds(x, y, largura, altura);
        painel.add(titulo);
    }

    private void adicionarCampo(JPanel painel, String rotulo, JTextField campo, int y) {
        JLabel label = new JLabel(rotulo);
        label.setBounds(60, y, 100, 25);
        campo.setBounds(160, y, 620, 25);

        painel.add(label);
        painel.add(campo);
    }

    private void adicionarBotao(JPanel painel, String texto, int x, Runnable acao) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, 325, 120, 35);
        botao.addActionListener(e -> acao.run());
        painel.add(botao);
    }

    private void configurarSelecaoDaTabela() {
        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tabelaClientes.getSelectedRow();

                if (linha >= 0) {
                    preencherCampos(linha);
                }
            }
        });
    }


    // =====================================================
    // AÇÕES DOS BOTÕES
    // =====================================================

    private void salvarCliente() {
        if (formularioIncompleto()) {
            mostrarMensagem("Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cpf = texto(campoCpf);

        if (CadastroCliente.cpfExiste(cpf)) {
            mostrarMensagem("Este CPF já está cadastrado!", "CPF duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean salvo = CadastroCliente.salvar(
                texto(campoNome),
                cpf,
                texto(campoTelefone),
                texto(campoEmail),
                texto(campoEndereco)
        );

        if (salvo) {
            mostrarMensagem("Cliente cadastrado com sucesso!", "Cadastro realizado",
                    JOptionPane.INFORMATION_MESSAGE);
            atualizarTela();
        } else {
            mostrarMensagem("Não foi possível cadastrar o cliente.\nVerifique o console do IntelliJ.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarCliente() {
        if (formularioIncompleto()) {
            mostrarMensagem("Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cpf = texto(campoCpf);
        String nome = texto(campoNome);

        boolean confirmado = confirmar(
                "Deseja realmente alterar este cliente?\n\nCPF: " + cpf + "\nNome: " + nome,
                "Confirmar alteração",
                JOptionPane.QUESTION_MESSAGE
        );

        if (!confirmado) {
            return;
        }

        boolean sucesso = CadastroCliente.alterar(
                cpf,
                nome,
                texto(campoTelefone),
                texto(campoEmail),
                texto(campoEndereco)
        );

        if (sucesso) {
            mostrarMensagem("Cliente alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            atualizarTela();
        } else {
            mostrarMensagem("Cliente não encontrado ou não foi possível alterar.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirCliente() {
        String cpf = texto(campoCpf);

        if (cpf.isEmpty()) {
            mostrarMensagem("Selecione um cliente na tabela primeiro!", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean confirmado = confirmar(
                "Tem certeza que deseja excluir este cliente?\n\nCPF: " + cpf
                        + "\nNome: " + campoNome.getText(),
                "Confirmar exclusão",
                JOptionPane.WARNING_MESSAGE
        );

        if (!confirmado) {
            return;
        }

        if (CadastroCliente.excluir(cpf)) {
            mostrarMensagem("Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            atualizarTela();
        } else {
            mostrarMensagem("Não foi possível excluir o cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarAoMenu() {
        new TelaMenu().setVisible(true);
        dispose();
    }


    // =====================================================
    // TABELA E FORMULÁRIO
    // =====================================================

    private void carregarClientes() {
        modeloTabela.setRowCount(0);

        List<Cliente> clientes = CadastroCliente.listarClientes();

        for (Cliente cliente : clientes) {
            modeloTabela.addRow(new Object[]{
                    cliente.getCpf(),
                    cliente.getNome(),
                    cliente.getTelefone(),
                    cliente.getEmail(),
                    cliente.getEndereco()
            });
        }
    }

    private void preencherCampos(int linha) {
        campoCpf.setText(celula(linha, 0));
        campoNome.setText(celula(linha, 1));
        campoTelefone.setText(celula(linha, 2));
        campoEmail.setText(celula(linha, 3));
        campoEndereco.setText(celula(linha, 4));
    }

    private void limparCampos() {
        campoNome.setText("");
        campoCpf.setText("");
        campoTelefone.setText("");
        campoEmail.setText("");
        campoEndereco.setText("");

        tabelaClientes.clearSelection();
    }

    private void atualizarTela() {
        limparCampos();
        carregarClientes();
    }


    // =====================================================
    // UTILITÁRIOS
    // =====================================================

    private String texto(JTextField campo) {
        return campo.getText().trim();
    }

    private String celula(int linha, int coluna) {
        return modeloTabela.getValueAt(linha, coluna).toString();
    }

    private boolean formularioIncompleto() {
        return texto(campoNome).isEmpty()
                || texto(campoCpf).isEmpty()
                || texto(campoTelefone).isEmpty()
                || texto(campoEmail).isEmpty()
                || texto(campoEndereco).isEmpty();
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