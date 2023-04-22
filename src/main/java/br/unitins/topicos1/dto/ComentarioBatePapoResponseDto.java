package br.unitins.topicos1.dto;

import lombok.Value;

import java.time.LocalDateTime;
@Value
public class ComentarioBatePapoResponseDto {
    Long id;
    String nome;
    String comentario;
    LocalDateTime dataCriacao;
    boolean vendedor;
}
