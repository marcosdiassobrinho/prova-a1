package br.unitins.topicos1.dto;

import lombok.Value;

@Value
public class AvaliacaoCompraDto {
    Long perfilId;
    String comentario;
    boolean recomenda;
}
