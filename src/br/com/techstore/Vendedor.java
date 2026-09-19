package br.com.techstore;

import java.math.BigDecimal;

public class Vendedor {

    private String matricula;
    private String nome;
    private BigDecimal percentualComissao;

    // CONSTRUTOR
    public Vendedor(
            String matricula,
            String nome,
            BigDecimal percentualComissao) {

        this.matricula = matricula;
        this.nome = nome;
        this.percentualComissao = percentualComissao;
    }

    // GET MATRÍCULA
    public String getMatricula() {
        return matricula;
    }

    // GET NOME
    public String getNome() {
        return nome;
    }

    // GET PERCENTUAL DE COMISSÃO
    public BigDecimal getPercentualComissao() {
        return percentualComissao;
    }

    @Override
    public String toString() {

        return "Vendedor{" +
                "matricula='" + matricula + '\'' +
                ", nome='" + nome + '\'' +
                ", percentualComissao=" + percentualComissao +
                '}';
    }
}