package br.unitins.topicos1.dto;

import lombok.Value;

import java.util.List;

@Value
public class AnuncioDto {
    Long perfilId;
    String titulo;
    List<VariacaoRequestDto> variacoes;
    String marca;
    String nomeProduto;
}
