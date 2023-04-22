package br.unitins.topicos1.dto;

import br.unitins.topicos1.domain.model.enums.FormaPagamento;
import br.unitins.topicos1.domain.model.AvaliacaoCompra;
import br.unitins.topicos1.domain.model.Variacao;
import br.unitins.topicos1.domain.model.enums.StatusPagamento;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CompraResponseDto {
    Long id;
    Double valorTotal;
    String nomeComprador;
    String tituloPagina;
    StatusPagamento statusPagamento;
    int quantidadeComprada;
    LocalDateTime dataEmissao;
    FormaPagamento formaPagamento;
    List<Variacao> variacoes;
    AvaliacaoCompra avaliacaoComprador;
    AvaliacaoCompra avaliacaoVendedor;


    public CompraResponseDto(Long id, Double valorTotal, String nomeComprador, String tituloPagina, StatusPagamento statusPagamento, int quantidadeComprada, LocalDateTime dataEmissao, FormaPagamento formaPagamento, AvaliacaoCompra avaliacaoComprador, AvaliacaoCompra avaliacaoVendedor) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.nomeComprador = nomeComprador;
        this.tituloPagina = tituloPagina;
        this.statusPagamento = statusPagamento;
        this.quantidadeComprada = quantidadeComprada;
        this.dataEmissao = dataEmissao;
        this.formaPagamento = formaPagamento;
        this.avaliacaoComprador = avaliacaoComprador;
        this.avaliacaoVendedor = avaliacaoVendedor;
    }

    public void setVariacoes(List<Variacao> variacoes) {
        this.variacoes = variacoes;
    }
}
