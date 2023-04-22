package br.unitins.topicos1.dto;

import lombok.Value;

@Value
public class AnunciosResponseDto {
    Long idAnuncio;
    String nomeVendedor;
    String titulo;
    double valorMinimo;
    double valorMaximo;
    Long quantidadeDisponivel;
}
