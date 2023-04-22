package br.unitins.topicos1.dto;

import br.unitins.topicos1.domain.model.Produto;
import lombok.Value;

@Value
public class AnuncioResponseDto {
    Produto produto;
    String titulo;
    String nomeVendedor;
}
