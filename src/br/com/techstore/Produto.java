package br.com.techstore;

import java.math.BigDecimal;

public class Produto {

    private String codigo;
    private String nome;
    private String embalagem;
    private String tamanho;
    private String tipo;
    private BigDecimal preco;
    private int estoque;

    public Produto(
            String codigo,
            String nome,
            String embalagem,
            String tamanho,
            String tipo,
            BigDecimal preco,
            int estoque) {

        this.codigo = codigo;
        this.nome = nome;
        this.embalagem = embalagem;
        this.tamanho = tamanho;
        this.tipo = tipo;
        this.preco = preco;
        this.estoque = estoque;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getEmbalagem() {
        return embalagem;
    }

    public String getTamanho() {
        return tamanho;
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }

    @Override
    public String toString() {

        return "Produto{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", embalagem='" + embalagem + '\'' +
                ", tamanho='" + tamanho + '\'' +
                ", tipo='" + tipo + '\'' +
                ", preco=" + preco +
                ", estoque=" + estoque +
                '}';
    }
}