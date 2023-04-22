package br.unitins.topicos1.dto;

import lombok.Value;

@Value
public class VariacaoRequestDto {
     String descricao;
     Double peso;
     boolean usado;
     Double valorBruto;
     Integer quantidadeEstoque;
}
