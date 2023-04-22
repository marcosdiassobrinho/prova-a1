package br.unitins.topicos1.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ComentariosResponseDto {
    String comentario;
    String nomeUsuario;
    LocalDateTime dataComentario;
}
