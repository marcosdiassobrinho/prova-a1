package br.unitins.topicos1.dto;

import lombok.Value;

import java.util.List;

@Value
public class AtualizarVariacaoRequestDto {
    String decricao;
    Double valorBruto;
    Integer quantidadeEstoque;
    List<Integer> idVariacoes;
    double valorTotal;

}
